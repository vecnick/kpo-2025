package finance.commands.Operation;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class GetOperationsCommand implements ICommand {
    private FinanceFacade financeFacade;

    public GetOperationsCommand(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    @Override
    public void execute() {
        financeFacade.getAllOperations().forEach(operation -> {
            System.out.println();
            System.out.println(operation);
        });
    }
}
