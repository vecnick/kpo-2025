package HSEBank.Storages;

import HSEBank.Domains.Categories.Category;
import HSEBank.Interfaces.CategoriesStorageI;
import HSEBank.Interfaces.ExporterI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriesStorage implements CategoriesStorageI {
    List<Category> categories = new ArrayList<>();

    @Override
    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
        } else {
            throw new IllegalArgumentException("Operation already exists");
        }
    }

    @Override
    public void updateCategory(Category category) {
        if (categories.contains(category)) {
            categories.set(categories.indexOf(category), category);
        } else {
            throw new IllegalArgumentException("Operation does not exist");
        }
    }

    @Override
    public void deleteCategory(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                categories.remove(category);
                break;
            }
        }
    }

    @Override
    public int getCategoryIdByName(String categoryName) {
        var val = categories.stream().filter(category -> category.getName().equals(categoryName)).findFirst();

        if (val.isPresent()) {
            return val.get().getId();
        } else {
            return -1;
        }
    }

    @Override
    public Category getCategory(int id) {
        return categories.stream().filter(p -> p.getId() == id).toList().getFirst();
    }

    @Override
    public void export(ExporterI exporter) {
        try {
            exporter.exportCategories(categories);
        } catch (Exception e) {
            System.out.println("Error exporting operations " + e.getMessage());
        }
    }
}
