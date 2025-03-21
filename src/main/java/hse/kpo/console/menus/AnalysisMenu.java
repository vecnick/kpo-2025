package hse.kpo.console.menus;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.Date;

import org.springframework.expression.spel.ast.Identifier;
import org.springframework.stereotype.Component;

import hse.kpo.analytics.BankAnalytics;
import hse.kpo.console.InputReader;
import hse.kpo.console.application.ConsoleApplication;
import hse.kpo.console.interfaces.TerminalMenu;
import hse.kpo.console.params.ConsoleGetBankAccount;
import hse.kpo.console.params.ConsoleGetCategory;
import hse.kpo.console.params.ConsoleGetOperation;
import hse.kpo.exceptions.ValueException;
import hse.kpo.facades.BankFacade;
import hse.kpo.types.Id;
import lombok.AllArgsConstructor;

@Component("analysisMenu")
@AllArgsConstructor
public class AnalysisMenu implements TerminalMenu {
    BankAnalytics analytics;

    ConsoleApplication app;
    BankFacade facade;
    ConsoleGetBankAccount getBankAccount;
    ConsoleGetCategory getCategory;
    ConsoleGetOperation getOperation;
    InputReader reader;
    PrintStream out;

    @Override
    public void print() {
        out.println("Analysis menu");
        out.println("1. Profit by id and period");
        out.println("2. Group by all categories");
        out.println("0. Back to main menu");
    }


    @Override
    public void read_answer() throws ParseException, ValueException {
        var action = reader.readInt("\n");
        if (action == 0) {
            app.switchState("initialMenu");
        } else if (action == 1) {
            Id id = new Id(reader.readInt("Type id: "));
            Date startDate = reader.readDate("Type start date: ");
            Date endDate = reader.readDate("Type end date: ");
            out.println(analytics.countProfitbyPeriod(id, startDate, endDate));
        } else if (action == 2) {
            out.println(analytics.moneyByCategories());
        }
    }
}
