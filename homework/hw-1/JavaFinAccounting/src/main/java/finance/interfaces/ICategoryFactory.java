package finance.interfaces;

import finance.domains.Category;

public interface ICategoryFactory {
    Category createCategory(int id, int type, String name);
}
