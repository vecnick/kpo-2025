package HSEBank.Interfaces;

import HSEBank.Domains.Categories.Category;
import HSEBank.Enums.OperationTypes;

public interface CategoryFactoryI {
    Category createCategory(String categoryName, OperationTypes operationType);
}
