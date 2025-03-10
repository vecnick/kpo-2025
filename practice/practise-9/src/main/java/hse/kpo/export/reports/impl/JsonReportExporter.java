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
