package finance.commands.Analytics;

import finance.domains.BankAccount;
import finance.domains.Operation;
import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.util.List;
import java.util.Map;

public class GroupByAccountsCommand implements ICommand {
    private FinanceFacade financeFacade;

    public GroupByAccountsCommand(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    @Override
    public void execute() {
        Map<BankAccount, List<Operation>> groupedOperations = financeFacade.groupByAccounts();

        for (Map.Entry<BankAccount, List<Operation>> entry : groupedOperations.entrySet()) {
            BankAccount account = entry.getKey();
            List<Operation> operations = entry.getValue();

            System.out.println();
            System.out.println("    Account: " + account.getName() + "(" + account.getId() + ")");
            for (Operation operation : operations) {
                System.out.println();
                System.out.println(operation);
            }
        }
    }
}
