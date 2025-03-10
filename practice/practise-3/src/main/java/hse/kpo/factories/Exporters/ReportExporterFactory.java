package hse.kpo.factories.Exporters;

import hse.kpo.Enums.ReportFormat;
import hse.kpo.Exporters.Reports.ReportExporter;
import hse.kpo.Exporters.Reports.JsonReportExporter;
import hse.kpo.Exporters.Reports.MarkdownReportExporter;
import lombok.RequiredArgsConstructor;
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
