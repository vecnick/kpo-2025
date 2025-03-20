package finance.commands.BankAccount;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class GetAccountsCommand implements ICommand {
    private FinanceFacade financeFacade;

    public GetAccountsCommand(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    @Override
    public void execute() {
        financeFacade.getAllBankAccounts().forEach(account -> {
            System.out.println();
            System.out.println(account);
        });
    }
}
