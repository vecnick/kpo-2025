package hse.kpo.factories.Exporters;

import hse.kpo.Enums.ReportFormat;
import hse.kpo.Enums.TransportReportFormat;
import hse.kpo.Exporters.Reports.ReportExporter;
import hse.kpo.Exporters.Reports.JsonReportExporter;
import hse.kpo.Exporters.Reports.MarkdownReportExporter;
import hse.kpo.Exporters.Transport.CsvTransportExporter;
import hse.kpo.Exporters.Transport.TransportExporter;
import hse.kpo.Exporters.Transport.XmlTransportExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TransportExporterFactory {
    public TransportExporter create(TransportReportFormat format) {
        return switch (format) {
            case CSV -> new CsvTransportExporter();
            case XML -> new XmlTransportExporter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}