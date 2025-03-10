package hse.kpo.Exporters.Reports;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.domains.Reports.Report;
import hse.kpo.interfaces.Transport;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class JsonReportExporter implements ReportExporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void export(Report report, Writer writer) throws IOException {
        objectMapper.writeValue(writer, report);
    }
}