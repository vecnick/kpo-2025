package hse.kpo.Exporters.Reports;

import hse.kpo.domains.Reports.Report;
import hse.kpo.interfaces.TransportExporter;

import java.io.IOException;
import java.io.Writer;

public interface ReportExporter {
    void export(Report report, Writer writer) throws IOException;
}
