package finance.commands.Analytics;

import finance.domains.Category;
import finance.domains.Operation;
import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.util.List;
import java.util.Map;

public class GroupByCategoriesCommand implements ICommand {
    private FinanceFacade financeFacade;

    public GroupByCategoriesCommand(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    @Override
    public void execute() {
        Map<Category, List<Operation>> groupedOperations = financeFacade.groupByCategories();

        for (Map.Entry<Category, List<Operation>> entry : groupedOperations.entrySet()) {
            Category category = entry.getKey();
            List<Operation> operations = entry.getValue();

            System.out.println();
            System.out.println("    Category: " + category.getName() + "(" + category.getId() + ")");
            for (Operation operation : operations) {
                System.out.println();
                System.out.println(operation);
            }
        }
    }
}
