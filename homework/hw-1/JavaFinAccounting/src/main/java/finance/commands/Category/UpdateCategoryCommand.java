package finance.commands.Category;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class UpdateCategoryCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final int id;

    private final String name;

    public UpdateCategoryCommand(FinanceFacade financeFacade, int id, String name) {
        this.financeFacade = financeFacade;
        this.id = id;
        this.name = name;
    }

    @Override
    public void execute() {
        financeFacade.updateCategory(id, name);
        System.out.println("Category " + id + " was updated: " + name);
    }
}
