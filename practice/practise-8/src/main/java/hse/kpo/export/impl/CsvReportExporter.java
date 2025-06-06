package hse.kpo.export.impl;

import hse.kpo.domains.Report;
import hse.kpo.export.ReportExporter;

import java.io.IOException;
import java.io.Writer;


public class CsvReportExporter implements ReportExporter {
    @Override
    public void export(Report report, Writer writer) throws IOException {
        writer.write(report.content());
        writer.flush();
    }
}