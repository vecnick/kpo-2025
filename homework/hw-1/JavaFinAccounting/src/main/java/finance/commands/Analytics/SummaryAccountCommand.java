package finance.commands.Analytics;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.time.LocalDate;

public class SummaryAccountCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    private final LocalDate startDate;

    private final LocalDate endDate;

    public SummaryAccountCommand(FinanceFacade financeFacade, int id,
                                 LocalDate startDate, LocalDate endDate) {
        this.financeFacade = financeFacade;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void execute() {
        double summary = financeFacade.getSummaryForAccount(id, startDate, endDate);
        System.out.println("Summary for " + id +
                " account on (" + startDate + "; " + endDate + ") period: " + summary);
    }
}
