package HSEBank.Factories;

import HSEBank.Domains.Categories.Category;
import HSEBank.Enums.OperationTypes;
import HSEBank.Interfaces.CategoryFactoryI;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory implements CategoryFactoryI {
    private int curId = 0;

    @Override
    public Category createCategory(String categoryName, OperationTypes operationType) {
        return new Category(curId++, categoryName, operationType);
    }
}
