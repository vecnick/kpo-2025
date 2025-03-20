package finance.commands.BankAccount;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class UpdateAccountCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    private final String name;

    private final double balance;

    public UpdateAccountCommand(FinanceFacade financeFacade, int id, String name, double balance) {
        this.financeFacade = financeFacade;
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void execute() {
        financeFacade.updateBankAccount(id, name, balance);
        System.out.println("Account " + id + " was updated: " + name);
    }
}
