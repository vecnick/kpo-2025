package hse.kpo.factories;

import hse.kpo.enums.ReportFormat;
import hse.kpo.export.reports.ReportExporter;
import hse.kpo.export.reports.impl.JsonReportExporter;
import hse.kpo.export.reports.impl.MarkdownReportExporter;
import hse.kpo.export.transport.TransportExporter;
import hse.kpo.export.transport.impl.CsvTransportExporter;
import hse.kpo.export.transport.impl.XmlTransportExporter;
import org.springframework.stereotype.Component;

@Component
public class TransportExporterFactory {
    public TransportExporter create(ReportFormat format) {
        return switch (format) {
            case XML -> new XmlTransportExporter();
            case CSV -> new CsvTransportExporter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
