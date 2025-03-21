package hse.kpo.console.menus;

import java.io.PrintStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.console.application.ConsoleApplication;
import hse.kpo.console.interfaces.TerminalMenu;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("initialMenu")
public class InitialMenu implements TerminalMenu {

    @Autowired
    ConsoleApplication app;

    @Autowired
    InputReader reader;

    @Autowired
    PrintStream out;

    public void print()
    {
        out.println("Actions:");
        out.println("1. Add objects");
        out.println("2. Delete objects");
        out.println("3. Print objects");
        out.println("4. Analysis module");
        out.println("5. Export data");
        out.println("0. Exit");
    }

    public void read_answer()
    {
        var val = reader.readInt("\n");
        if (val == 0) {
            System.exit(0);
        } else if (val == 1) {
            app.switchState("addMenu");
        } else if (val == 2) {
            app.switchState("deleteMenu");
        } else if (val == 3) {
            app.switchState("printMenu");
        } else if (val == 4) {
            app.switchState("analysisMenu");
        } else if (val == 5) {
            app.switchState("exportMenu");
        } else {
            out.println("expected 0-5 number");
        }
    }
}

