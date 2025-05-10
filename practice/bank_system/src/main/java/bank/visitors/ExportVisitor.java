package bank.visitors;

import bank.report.Report;
import bank.exporter.ExporterCSV;
import bank.exporter.ExporterJSON;
import bank.exporter.ExporterYAML;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
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
