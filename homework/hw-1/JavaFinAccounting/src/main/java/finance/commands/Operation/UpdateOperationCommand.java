package finance.commands.Operation;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.time.LocalDate;

public class UpdateOperationCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    private final int type;

    private final double amount;

    private final LocalDate date;

    private final int bankAccountId;

    private final int categoryId;

    private final String description;

    public UpdateOperationCommand(FinanceFacade financeFacade, int id, int type, double amount, LocalDate date,
                                  int bankAccountId, int categoryId, String description) {
        this.financeFacade = financeFacade;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.bankAccountId = bankAccountId;
        this.categoryId = categoryId;
        this.description = description;
    }

    @Override
    public void execute() {
        financeFacade.updateOperation(id, type, amount, date, bankAccountId, categoryId, description);
        System.out.println("Operation " + id + " was updated");
    }
}
