package bank.Factories;

import bank.enums.ReportFormat;
import bank.exporter.ExporterCSV;
import bank.exporter.ExporterJSON;
import bank.exporter.ExporterYAML;
import bank.exporter.Exporter;
import org.springframework.stereotype.Component;

@Component
public class ExporterFactory {
    public Exporter create(ReportFormat format) throws IllegalAccessException {
        return switch (format) {
            case CSV -> new ExporterCSV();
            case JSON -> new ExporterJSON();
            case YAML -> new ExporterYAML();
            default -> throw new IllegalAccessException("Unsupported format: " + format);
        };
    }
}
