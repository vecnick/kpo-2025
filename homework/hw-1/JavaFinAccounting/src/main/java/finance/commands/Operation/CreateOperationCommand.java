package finance.commands.Operation;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.time.LocalDate;

public class CreateOperationCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int type;

    private final double amount;

    private final LocalDate date;

    private final int bankAccountId;

    private final int categoryId;

    private final String description;

    public CreateOperationCommand(FinanceFacade financeFacade, int type, double amount, LocalDate date,
                                  int bankAccountId, int categoryId, String description) {
        this.financeFacade = financeFacade;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.bankAccountId = bankAccountId;
        this.categoryId = categoryId;
        this.description = description;
    }

    @Override
    public void execute() {
        financeFacade.createOperation(type, amount, date, bankAccountId, categoryId, description);
        System.out.println("Operation was created");
    }
}
