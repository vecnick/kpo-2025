# Занятие 8. DDD

## Цель занятия
- Разобраться что такое DDD. Дополнить проект работой с файлами, а в частности формированием отчетов разных форматов.
## Требования к реализации
1. Разделить сущности по доменам (машины, катамараны и покупатели)
2. Добавить возможность экспорта отчетов в формате MARKDOWN и json.
3. Добавить возможность экспорта транспорта в форматах csv и xml.
## Тестирование
1. После выполнения программы у вас формируются отчеты.
## Задание на доработку
- Добавить возможность добавления транспорта из отчета.
- Добавить парсинг отчета (разделение команд внутри value)
## Пояснения к реализации
Добавьте зависимость Jackson в build.gradle, для включение адаптера обычных классов в json формат:
```
implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
```

Добавьте перечисление для форматов отчета:

```
public enum ReportFormat {
JSON,
MARKDOWN
}
```

Создайте абстрактный класс экспортера:

```
package hse.kpo.export.reports;

import hse.kpo.domains.Report;
import java.io.IOException;
import java.io.Writer;

public interface ReportExporter {
void export(Report report, Writer writer) throws IOException;
}
```

Реализуйте экспорт для типов [json](/report.json) и [MARKDOWN](/report.MD):
```
package hse.kpo.export.reports.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.domains.Report;
import hse.kpo.export.reports.ReportExporter;

import java.io.IOException;
import java.io.Writer;

public class JsonReportExporter implements ReportExporter {
private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void export(Report report, Writer writer) throws IOException {
        objectMapper.writeValue(writer, report);
    }
}
```

```
package hse.kpo.export.reports.impl;

import hse.kpo.domains.Report;
import hse.kpo.export.reports.ReportExporter;

import java.io.IOException;
import java.io.Writer;

public class MarkdownReportExporter implements ReportExporter {
@Override
public void export(Report report, Writer writer) throws IOException {
writer.write("# " + report.title() + "\n\n");
writer.write(report.content());
writer.flush();
}
}
```

Реализуйте фабрику экспортеров:

```
package hse.kpo.factories;

import hse.kpo.enums.ReportFormat;
import hse.kpo.export.reports.ReportExporter;
import hse.kpo.export.reports.impl.JsonReportExporter;
import hse.kpo.export.reports.impl.MarkdownReportExporter;
import org.springframework.stereotype.Component;

@Component
public class ReportExporterFactory {
public ReportExporter create(ReportFormat format) {
return switch (format) {
case JSON -> new JsonReportExporter();
case MARKDOWN -> new MarkdownReportExporter();
default -> throw new IllegalArgumentException("Unsupported format: " + format);
};
}
}
```

Интегрируйте экспорт отчетов в класс Hse:

```
package hse.kpo.facade;

// ... импорты ...

@Component
@RequiredArgsConstructor
public class Hse {
// ... существующие зависимости ...
private final ReportExporterFactory reportExporterFactory;

    public void exportReport(ReportFormat format, Writer writer) {
        Report report = salesObserver.buildReport();
        ReportExporter exporter = reportExporterFactory.create(format);

        try {
            exporter.export(report, writer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // Старый метод для обратной совместимости
    public String generateReport() {
        return salesObserver.buildReport().toString();
    }
}
```

Добавить использование в главный тест/main:

```
// Экспорт в консоль в формате Markdown
hse.exportReport(ReportFormat.MARKDOWN, new PrintWriter(System.out));
// Экспорт в файл в формате MARKDOWN
try (FileWriter fileWriter = new FileWriter("report.MD")) {
hse.exportReport(ReportFormat.MARKDOWN, fileWriter);
}

// Экспорт в файл в формате JSON
try (FileWriter fileWriter = new FileWriter("report.json")) {
hse.exportReport(ReportFormat.JSON, fileWriter);
}
```

Задание экспорта транспорта:
Необходимо добавить общий интерфейс для машин и катамаранов и реализовать его методы.
```
public interface Transport {
boolean isCompatible(Customer customer);
int getVin(); 
String getEngineType();
String getTransportType();
}
```

Сигнатура транспортых экспортеров:
```
public interface TransportExporter {
void export(List<Transport> transports, Writer writer) throws IOException;
}
```

Пример экспорта для типа [csv](/transports.csv):

```
String.format("%d,%s,%s\n",
transport.getVin(),
transport.getTransportType(),
transport.getEngineType());
```

Пример экспорта для типа [xml](/transports.xml):
```
String.format("""
<Vehicle>
    <VIN>%d</VIN>
    <Type>%s</Type>
    <Engine>
        <Type>%s</Type>
    </Engine>
</Vehicle>
""",
transport.getVin(),
transport.getTransportType(),
transport.getEngineType()
)
```

Для добавления машин и катамаранов в фасаде можно использовать Stream.concat
```
List<Transport> transports = Stream.concat(
carStorage.getCars().stream(),
catamaranStorage.getCatamarans().stream())
.toList();
```

<details> 
<summary>Ссылки</summary>
1. https://javarush.com/quests/lectures/jru.module2.lecture31
2. 
</details>