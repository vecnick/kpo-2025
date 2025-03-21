package hse.kpo.console.interfaces;

import java.io.PrintStream;
import java.text.ParseException;

import hse.kpo.console.InputReader;
import hse.kpo.exceptions.ValueException;

public interface TerminalMenu {
    void print();
    void read_answer() throws ValueException, ParseException;
}
