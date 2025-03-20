package finance.commands.Analytics;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.time.LocalDate;

public class SummaryAllCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final LocalDate startDate;

    private final LocalDate endDate;

    public SummaryAllCommand(FinanceFacade financeFacade, LocalDate startDate, LocalDate endDate) {
        this.financeFacade = financeFacade;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void execute() {
        double summary = financeFacade.getSummaryForAll(startDate, endDate);
        System.out.println("Summary for all accounts on (" + startDate + "; " + endDate + ") period: " + summary);
    }
}
