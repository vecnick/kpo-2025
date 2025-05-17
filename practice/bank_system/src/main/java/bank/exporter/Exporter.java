package bank.exporter;

import bank.report.Report;
import bank.visitors.ExportVisitor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
public abstract class Exporter {
    public abstract <T> void export(Writer writer, Report<T> report) throws IOException;

    public abstract <T> void accept(ExportVisitor visitor, Report<T> report) throws IOException;
}
