package hse.kpo.console.menus;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.console.application.ConsoleApplication;
import hse.kpo.console.interfaces.TerminalMenu;
import hse.kpo.console.params.ConsoleGetBankAccount;
import hse.kpo.console.params.ConsoleGetCategory;
import hse.kpo.console.params.ConsoleGetOperation;
import hse.kpo.enums.OperationCreationStatus;
import hse.kpo.exceptions.ValueException;
import hse.kpo.facades.BankFacade;
import hse.kpo.params.BankAccountParams;
import hse.kpo.params.CategoryParams;
import hse.kpo.params.OperationParams;
import lombok.AllArgsConstructor;

@Component("addMenu")
@AllArgsConstructor
public class AddMenu implements TerminalMenu {

    ConsoleApplication app;
    BankFacade facade;
    ConsoleGetBankAccount getBankAccount;
    ConsoleGetCategory getCategory;
    ConsoleGetOperation getOperation;
    InputReader reader;
    PrintStream out;

    public void print() {
        out.println("actions:");
        out.println("1. Add bank account");
        out.println("2. Add category");
        out.println("3. Add operation");
        out.println("0. Back to main menu");
    }

    @Override
    public void read_answer() throws ValueException, ParseException {
        var action = reader.readInt("\n");
        if (action == 0) {
            app.switchState("initialMenu");
        } else if (action == 1) {
            facade.createBankAccount(new BankAccountParams(getBankAccount.getName(), getBankAccount.getBalance()));
        } else if (action == 2) {
            facade.createCategory(new CategoryParams(getCategory.getType(), getCategory.getName()));
        } else if (action == 3) {
            OperationCreationStatus res = facade
                    .createOperation(new OperationParams(getOperation.getType(), getOperation.getBankAccountId(),
                            getOperation.getAmount(), getOperation.getDate(), getOperation.getDescription(),
                            getOperation.getCategoryId()));
            if (res != OperationCreationStatus.SUCCESS) {
                out.println("couldn't add operation due to inexistent category or bank account id");
            }
        } else {
            out.println("expected 0-3 number");
        }
    }
}