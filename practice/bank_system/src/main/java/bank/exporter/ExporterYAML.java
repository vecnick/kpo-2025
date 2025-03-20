package bank.exporter;

import bank.report.Report;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.io.Writer;

public class ExporterYAML extends Exporter {
    private final YAMLMapper yamlMapper = new YAMLMapper();

    @Override
    public <T> void export(Writer writer, Report<T> report) throws IOException {
        yamlMapper.writeValue(writer, report);
    }

    @Override
    public <T> void accept(ExportVisitor visitor, Report<T> report) throws IOException {
        visitor.visit(this, report);
    }
}
