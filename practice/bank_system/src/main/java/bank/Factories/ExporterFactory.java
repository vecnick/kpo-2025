package bank.Factories;

import bank.enums.ReportFormat;
import bank.export.ExporterCSV;
import bank.export.ExporterJSON;
import bank.export.ExporterYAML;
import bank.export.Exporter;

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
