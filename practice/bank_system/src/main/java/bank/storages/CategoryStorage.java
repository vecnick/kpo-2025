package bank.storages;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.ICategoryFactory;
import bank.interfaces.ICategoryStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryStorage implements ICategoryStorage {
    private List<Category> categories = new ArrayList<>();
    private int categoriesCounter = 0;

    @Override
    public void addCategory(ICategoryFactory categoryFactory, OperationType type, String name) {
        Category category = categoryFactory.create(categoriesCounter, type, name);
        categories.add(category);
        ++categoriesCounter;
    }

    @Override
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public void removeCategoryById(int id) {
        categories.removeIf(cat -> cat.getId() == id);
    }
}
