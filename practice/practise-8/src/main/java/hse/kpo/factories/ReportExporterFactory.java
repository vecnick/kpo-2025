package hse.kpo.factories;

import hse.kpo.enums.ReportFormat;
import hse.kpo.export.ReportExporter;
import hse.kpo.export.impl.JsonReportExporter;
import hse.kpo.export.impl.MarkdownReportExporter;
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