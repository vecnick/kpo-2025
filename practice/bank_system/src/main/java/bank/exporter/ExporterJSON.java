package bank.exporter;

import bank.report.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.io.Writer;

public class ExporterJSON extends Exporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> void export(Writer writer, Report<T> report) throws IOException {
        objectMapper.writeValue(writer, report);
    }

    @Override
    public <T> void accept(ExportVisitor visitor, Report<T> report) throws IOException {
        visitor.visit(this, report);
    }
}
