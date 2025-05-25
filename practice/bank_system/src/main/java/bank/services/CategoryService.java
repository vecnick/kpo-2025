package bank.services;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IBankAccountFactory;
import bank.interfaces.IBankAccountStorage;
import bank.interfaces.ICategoryFactory;
import bank.interfaces.ICategoryStorage;
import bank.report.ReportBankAccount;
import bank.report.ReportCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryService {
    private final ICategoryStorage categoryStorage;

    public CategoryService(ICategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
    }

    public void addCategory(ICategoryFactory categoryFactory, OperationType type, String name) {
        categoryStorage.addCategory(categoryFactory, type, name);
    }

    public void fillCategoriesByReports(List<ReportCategory> reports) {
        List<Category> categories = reports.stream()
                .map(report -> new Category(
                        report.id,
                        report.type,
                        report.name
                )).collect(Collectors.toList());

        categoryStorage.setCategories(categories);
    }

    public List<Category> getCategories() {
        return categoryStorage.getCategories();
    }

    public Optional<Category> getCategoryById(int id) {
        return getCategories().stream()
                .filter(cat -> cat.getId() == id) // фильтруем
                .findFirst(); // берём первый попавшийся
    }

    public void removeCategoryById(int id) {
        categoryStorage.removeCategoryById(id);
    }

    static public List<Category> getCategoriesIncome(List<Category> categories) {
        return categories.stream()
                .filter(cat -> cat.getType() == OperationType.INCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }

    static public List<Category> getCategoriesOutcome(List<Category> categories) {
        return categories.stream()
                .filter(cat -> cat.getType() == OperationType.OUTCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }
}
