package HSEBank.Factories;

import HSEBank.Exporters.CsvExporter;
import HSEBank.Exporters.JsonExporter;
import HSEBank.Exporters.YamlExporter;
import HSEBank.Interfaces.ExporterFactoryI;
import HSEBank.Interfaces.ExporterI;
import HSEBank.Services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ExporterFactory  implements ExporterFactoryI {

    @Override
    public ExporterI createExporter(String fileName) {
        var parts = fileName.split("\\.");

        if (parts.length == 2) {
            switch (parts[1]) {
                case "csv":
                    return new CsvExporter(fileName);
                case "json":
                    return new JsonExporter(fileName);
                case "yaml":
                    return new YamlExporter(fileName);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
}