package finance.commands.Category;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class CreateCategoryCommand implements ICommand {
    private FinanceFacade financeFacade;

    private int type;

    private String name;

    public CreateCategoryCommand(FinanceFacade financeFacade, int type, String name) {
        this.financeFacade = financeFacade;
        this.type = type;
        this.name = name;
    }

    @Override
    public void execute() {
        financeFacade.createCategory(type, name);
        System.out.println("Category was created: " + name);
    }
}
