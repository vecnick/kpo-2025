package hse.kpo.console.menus;

import java.io.PrintStream;
import java.text.ParseException;

import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.console.application.ConsoleApplication;
import hse.kpo.console.interfaces.TerminalMenu;
import hse.kpo.console.params.ConsoleGetBankAccount;
import hse.kpo.console.params.ConsoleGetCategory;
import hse.kpo.console.params.ConsoleGetOperation;
import hse.kpo.exceptions.ValueException;
import hse.kpo.facades.BankFacade;
import hse.kpo.params.BankAccountParams;
import hse.kpo.params.CategoryParams;
import hse.kpo.params.OperationParams;
import lombok.AllArgsConstructor;

@Component("printMenu")
@AllArgsConstructor
public class PrintMenu implements TerminalMenu {

    ConsoleApplication app;
    BankFacade facade;
    ConsoleGetBankAccount getBankAccount;
    ConsoleGetCategory getCategory;
    ConsoleGetOperation getOperation;
    InputReader reader;
    PrintStream out;

    public void print() {
        out.println("1. Print bank accounts");
        out.println("2. Print categories");
        out.println("3. Print operations");
        out.println("0. Back to main menu");
    }

    @Override
    public void read_answer() throws ValueException, ParseException {
        var action = reader.readInt("\n");
        if (action == 0) {
            app.switchState("initialMenu");
        } else if (action == 1) {
            facade.getBankAccounts().forEach(System.out::println);
        } else if (action == 2) {
            facade.getCategories().forEach(System.out::println);
        } else if (action == 3) {
            facade.getOperations().forEach(System.out::println);
        } else {
            out.println("expected 0-3 number");
        }
    }
}
