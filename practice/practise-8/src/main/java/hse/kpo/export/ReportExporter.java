package hse.kpo.export;

import hse.kpo.domains.Report;
import java.io.IOException;
import java.io.Writer;

public interface ReportExporter {
    void export(Report report, Writer writer) throws IOException;
}