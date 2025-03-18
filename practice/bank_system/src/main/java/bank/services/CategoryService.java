package bank.services;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IBankAccountFactory;
import bank.interfaces.IBankAccountStorage;
import bank.interfaces.ICategoryFactory;
import bank.interfaces.ICategoryStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService {
    private final ICategoryStorage categoryStorage;

    public CategoryService(ICategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
    }

    public void addCategory(ICategoryFactory categoryFactory, OperationType type, String name) {
        categoryStorage.addCategory(categoryFactory, type, name);
    }

    public List<Category> getCategories() {
        return categoryStorage.getCategories();
    }

    public Optional<Category> getOperationById(int id) {
        return getCategories().stream()
                .filter(cat -> cat.getId() == id) // фильтруем
                .findFirst(); // берём первый попавшийся
    }

    public List<Category> getCategoriesIncome() {
        return getCategories().stream()
                .filter(cat -> cat.getType() == OperationType.INCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }

    public List<Category> getCategoriesOutcome() {
        return getCategories().stream()
                .filter(cat -> cat.getType() == OperationType.OUTCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }
}
