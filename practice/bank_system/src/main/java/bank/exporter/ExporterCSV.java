package bank.exporter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import bank.report.Report;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.io.Writer;

public class ExporterCSV extends Exporter {
    private final CsvMapper csvMapper = new CsvMapper();

    @Override
    public <T> void export(Writer writer, Report<T> report) throws IOException {
        writer.write(report.title() + "\n");

        if (report.content().isEmpty()) {
            writer.flush();
            return;
        }

        // Определеяем класс (который требует CsvSchema) через первый элемент в списке
        Class<?> clazz = report.content().get(0).getClass();

        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
        csvMapper.writer(schema).writeValue(writer, report.content());
    }

    @Override
    public <T> void accept(ExportVisitor visitor, Report<T> report) throws IOException {
        visitor.visit(this, report);
    }
}
