package bank.export;

import bank.enums.DomainType;
import bank.report.Report;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class Exporter {
    public abstract <T> void export(Writer writer, Report<T> report) throws IOException;

    public abstract <T> void accept(ExportVisitor visitor, Report<T> report) throws IOException;
}
