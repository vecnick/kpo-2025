package finance.commands.BankAccount;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class CreateAccountCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final String name;

    private final double balance;

    public CreateAccountCommand(FinanceFacade financeFacade, String name, double balance) {
        this.financeFacade = financeFacade;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void execute() {
        financeFacade.createBankAccount(name, balance);
        System.out.println("Account was created: " + name);
    }
}
