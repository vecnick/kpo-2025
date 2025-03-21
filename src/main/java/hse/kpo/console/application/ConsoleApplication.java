package hse.kpo.console.application;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.console.interfaces.TerminalMenu;
import hse.kpo.exceptions.ValueException;;

@Component
public class ConsoleApplication {
    @Autowired
    @Lazy
    @Qualifier("initialMenu")
    TerminalMenu menu;


    @Autowired
    @Lazy
    Map<String, TerminalMenu> states;

    public void switchState(String newState) {
        menu = states.get(newState);
        if (Objects.isNull(menu)) {
            throw new RuntimeException("Unknown state: " + newState);
        }
    }

    public void run() throws ValueException, ParseException {
        while (true) {
            menu.print();
            menu.read_answer();
        }
    }
}
