package finance.commands.BankAccount;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class DeleteAccountCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    public DeleteAccountCommand(FinanceFacade financeFacade, int id) {
        this.financeFacade = financeFacade;
        this.id = id;
    }

    @Override
    public void execute() {
        financeFacade.deleteBankAccount(id);
        System.out.println("Account " + id + " was deleted");
    }
}
