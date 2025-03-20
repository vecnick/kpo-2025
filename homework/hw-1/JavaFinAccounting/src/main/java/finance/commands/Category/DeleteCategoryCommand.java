package finance.commands.Category;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class DeleteCategoryCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    public DeleteCategoryCommand(FinanceFacade financeFacade, int id) {
        this.financeFacade = financeFacade;
        this.id = id;
    }

    @Override
    public void execute() {
        financeFacade.deleteCategory(id);
        System.out.println("Category " + id + " was deleted");
    }
}
