package bank.visitors;

import bank.enums.DomainType;
import bank.export.Exporter;
import bank.report.Report;
import bank.export.ExporterCSV;
import bank.export.ExporterJSON;
import bank.export.ExporterYAML;

import java.io.FileWriter;
import java.io.IOException;

public class ExportVisitor {
    public <T> void visit(ExporterCSV exporterCSV, Report<T> report) throws IOException {
        FileWriter fileWriter = new FileWriter("report.csv");
        exporterCSV.export(fileWriter, report);
    }

    public <T> void visit(ExporterYAML exporterYAML, Report<T> report) throws IOException {
        FileWriter fileWriter = new FileWriter("report.yaml");
        exporterYAML.export(fileWriter, report);
    }

    public <T> void visit(ExporterJSON exporterJSON, Report<T> report) throws IOException {
        FileWriter fileWriter = new FileWriter("report.json");
        exporterJSON.export(fileWriter, report);
    }
}
