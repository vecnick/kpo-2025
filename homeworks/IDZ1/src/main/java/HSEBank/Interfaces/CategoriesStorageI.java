package HSEBank.Interfaces;

import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.Operations.Operation;

public interface CategoriesStorageI {
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int id);
    int getCategoryIdByName(String categoryName);
    Category getCategory(int id);
    void export(ExporterI exporter);
}
