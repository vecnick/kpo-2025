package hse.kpo.console.menus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.Format;

import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.console.application.ConsoleApplication;
import hse.kpo.console.interfaces.TerminalMenu;
import hse.kpo.console.params.ConsoleGetBankAccount;
import hse.kpo.console.params.ConsoleGetCategory;
import hse.kpo.console.params.ConsoleGetOperation;
import hse.kpo.export.visitors.CSVvisitors.CsvVisitor;
import hse.kpo.export.visitors.JSONvisitors.JsonVisitor;
import hse.kpo.facades.BankFacade;
import lombok.AllArgsConstructor;

@Component("exportMenu")
@AllArgsConstructor
public class ExportMenu implements TerminalMenu {

    ConsoleApplication app;
    BankFacade facade;
    ConsoleGetBankAccount getBankAccount;
    ConsoleGetCategory getCategory;
    ConsoleGetOperation getOperation;
    InputReader reader;
    PrintStream out;
    CsvVisitor csvWriter;
    JsonVisitor jsonWriter;

    @Override
    public void print() {
        out.println("Available actions:");
        out.println("1. Export in csv format");
        out.println("2. Export in json format");
        out.println("0. Back to main menu");
    }

    @Override
    public void read_answer() {
        var action = reader.readInt("\n");
        if (action == 0) {
            app.switchState("initialMenu");
        } else {

            var filename = reader.readString("Path: ");
            var file = new File(filename);

            PrintWriter writer;
            try {
                writer = new PrintWriter(file);
                if (action == 1) {
                    csvWriter.writeCsv(writer);
                } else if (action == 2) {
                    jsonWriter.writeJson(writer);
                }
                writer.close();
            } catch (FileNotFoundException e) {
                out.println("cannot create/open file");
                return;
            }
    }
    }
}
