package finance.factories;

import finance.domains.Category;
import finance.interfaces.ICategoryFactory;

public class CategoryFactory implements ICategoryFactory {
    @Override
    public Category createCategory(int id, int type, String name) {
        if (type != 0 && type != 1) {
            throw new IllegalArgumentException("Category type must be 0 or 1.");
        }

        return new Category(id, type, name);
    }
}
