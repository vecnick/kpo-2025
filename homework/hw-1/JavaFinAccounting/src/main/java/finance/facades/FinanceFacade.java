package finance.facades;

import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;
import finance.interfaces.IExportable;
import finance.services.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FinanceFacade {
    AnalyticsService analyticsService = new AnalyticsService();

    BankAccountService bankAccountService = new BankAccountService();

    CategoryService categoryService = new CategoryService();

    OperationService operationService = new OperationService();

    ExportService exportService = new ExportService();

    ImportService importService = new ImportService();

    public void createBankAccount(String name, double balance) {
        bankAccountService.createBankAccount(name, balance);
    }

    public void updateBankAccount(int id, String name, double balance) {
        bankAccountService.updateBankAccount(id, name, balance);
    }

    public void deleteBankAccount(int id) {
        bankAccountService.deleteBankAccount(id);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    public void createCategory(int type, String name) {
        categoryService.createCategory(type, name);
    }

    public void updateCategory(int id, String name) {
        categoryService.updateCategory(id, name);
    }

    public void deleteCategory(int id) {
        categoryService.deleteCategory(id);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public void createOperation(int type, double amount, LocalDate date,
                                int bankAccountId, int categoryId, String description) {
        if (Objects.isNull(bankAccountService.getBankAccount(bankAccountId))) {
            throw new IllegalArgumentException("There is no account with such id.");
        }

        if (Objects.isNull(categoryService.getCategory(categoryId))) {
            throw new IllegalArgumentException("There is no category with such id.");
        }

        if (type != categoryService.getCategory(categoryId).getType()) {
            throw new IllegalArgumentException("Operation type do not match the category type.");
        }

        operationService.createOperation(type, amount, date, bankAccountId, categoryId, description);
    }

    public void updateOperation(int id, int type, double amount, LocalDate date,
                                int bankAccountId, int categoryId, String description) {
        if (Objects.isNull(bankAccountService.getBankAccount(bankAccountId))) {
            throw new IllegalArgumentException("There is no account with such id.");
        }

        if (Objects.isNull(categoryService.getCategory(categoryId))) {
            throw new IllegalArgumentException("There is no category with such id.");
        }

        if (type != categoryService.getCategory(categoryId).getType()) {
            throw new IllegalArgumentException("Operation type do not match the category type.");
        }

        operationService.updateOperation(id, type, amount, date, bankAccountId, categoryId, description);
    }

    public void deleteOperation(int id) {
        operationService.deleteOperation(id);
    }

    public List<Operation> getAllOperations() {
        return operationService.getAllOperations();
    }

    public double getSummaryForAccount(int id, LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(bankAccountService.getBankAccount(id))) {
            throw new IllegalArgumentException("There is no account with such id.");
        }

        return analyticsService.getSummaryForAccount(getAllOperations(), id, startDate, endDate);
    }

    public double getSummaryForAll(LocalDate startDate, LocalDate endDate) {
        return analyticsService.getSummaryForAll(getAllOperations(), startDate, endDate);
    }

    public Map<Category, List<Operation>> groupByCategories() {
        return analyticsService.groupByCategories(getAllCategories(), getAllOperations());
    }

    public Map<BankAccount, List<Operation>> groupByAccounts() {
        return analyticsService.groupByAccounts(getAllBankAccounts(), getAllOperations());
    }

    public void csvExport(String accountsFileName, String categoriesFileName, String operationsFileName) {
        exportService.csvExportAccounts(getAllBankAccounts(), accountsFileName + ".csv");
        exportService.csvExportCategories(getAllCategories(), categoriesFileName + ".csv");
        exportService.csvExportOperations(getAllOperations(), operationsFileName + ".csv");
    }

    public void yamlExport(String accountsFileName, String categoriesFileName, String operationsFileName) {
        exportService.yamlExportAccounts(getAllBankAccounts(), accountsFileName + ".yaml");
        exportService.yamlExportCategories(getAllCategories(), categoriesFileName + ".yaml");
        exportService.yamlExportOperations(getAllOperations(), operationsFileName + ".yaml");
    }

    public void jsonExport(String accountsFileName, String categoriesFileName, String operationsFileName) {
        exportService.jsonExportAccounts(getAllBankAccounts(), accountsFileName + ".json");
        exportService.jsonExportCategories(getAllCategories(), categoriesFileName + ".json");
        exportService.jsonExportOperations(getAllOperations(), operationsFileName + ".json");
    }

    public void completeCsvImport(List<Map<?, ?>> data) {
        for (Map<?, ?> row : data) {
            if (row.containsKey("Balance")) {
                bankAccountService.importBankAccount(Integer.parseInt((String)row.get("ID")),
                        (String)row.get("Name"),
                        Double.parseDouble((String)row.get("Balance")));
            } else if (row.containsKey("Amount")) {
                operationService.importOperation(Integer.parseInt((String)row.get("ID")),
                        row.get("Type").equals("Income") ? 0 : 1,
                        Double.parseDouble((String)row.get("Amount")),
                        LocalDate.parse((String)row.get("Date")),
                        Integer.parseInt((String)row.get("BankAccountID")),
                        Integer.parseInt((String)row.get("CategoryID")),
                        (String)row.get("Description"));
            } else if (row.containsKey("Type")) {
                categoryService.importCategory(Integer.parseInt((String)row.get("ID")),
                        row.get("Type").equals("Income") ? 0 : 1,
                        (String)row.get("Name"));
            }
        }
    }

    public void completeJsonYamlImport(List<Map<?, ?>> data) {
        for (Map<?, ?> row : data) {
            if (row.containsKey("Balance")) {
                bankAccountService.importBankAccount((int)row.get("ID"),
                        (String)row.get("Name"),
                        (double)row.get("Balance"));
            } else if (row.containsKey("Amount")) {
                operationService.importOperation((int)row.get("ID"),
                        row.get("Type").equals("Income") ? 0 : 1,
                        (double)row.get("Amount"),
                        LocalDate.parse((String)row.get("Date")),
                        (int)row.get("BankAccountID"),
                        (int)row.get("CategoryID"),
                        (String)row.get("Description"));
            } else if (row.containsKey("Type")) {
                categoryService.importCategory((int)row.get("ID"),
                        row.get("Type").equals("Income") ? 0 : 1,
                        (String)row.get("Name"));
            }
        }
    }

    public void csvImport(String filePath) {
        completeCsvImport(importService.csvImport(filePath));
    }

    public void yamlImport(String filePath) {
        completeJsonYamlImport(importService.yamlImport(filePath));
    }

    public void jsonImport(String filePath) {
        completeJsonYamlImport(importService.jsonImport(filePath));
    }
}
