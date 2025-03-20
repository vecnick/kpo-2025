package finance.commands.Operation;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class DeleteOperationCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    public DeleteOperationCommand(FinanceFacade financeFacade, int id) {
        this.financeFacade = financeFacade;
        this.id = id;
    }

    @Override
    public void execute() {
        financeFacade.deleteOperation(id);
        System.out.println("Operation " + id + " was deleted");
    }
}
