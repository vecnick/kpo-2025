package HSEBank.Services;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Analytics.BankAccountAnalytics;
import HSEBank.Domains.Operations.Operation;
import HSEBank.Enums.OperationTypes;
import HSEBank.Factories.BankAccountFactory;
import HSEBank.Factories.CategoryFactory;
import HSEBank.Factories.OperationFactory;
import HSEBank.Interfaces.*;
import HSEBank.Storages.BankAccountsStorage;
import HSEBank.Storages.CategoriesStorage;
import HSEBank.Storages.OperationsStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BankAccountService implements BankAccountServiceI{
    private final BankAccountFactoryI bankAccountFactory;
    private final CategoryFactoryI categoryFactory;
    private final OperationFactoryI operationFactory;
    private final CategoriesStorageI categoriesStorage;
    private final OperationsStorageI operationsStorage;
    private final ExporterFactoryI exporterFactory;
    private final ImporterFactoryI importerFactory;
    private final BankAccountStorageI bankAccountsStorage;

    public int createBankAccount(String accountName) {
        var newBankAccount = bankAccountFactory.createBankAccount(accountName);

        try {
            bankAccountsStorage.addBankAccount(newBankAccount);
        } catch (Exception e) {
            return -1;
        }

        return newBankAccount.getId();
    }

    public Optional<BankAccount> getBankAccountDetails(int accountId) {
        var returnBankAccount = bankAccountsStorage.getBankAccount(accountId);
        return Optional.ofNullable(returnBankAccount);
    }

    public int deleteBankAccount(int accountId) {
        try {
            bankAccountsStorage.deleteBankAccount(accountId);
        } catch (IllegalArgumentException e) {
            return -1;
        }
        return accountId;
    }

    public int performOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, String categoryName, String description) {
        var bankAcc = bankAccountsStorage.getBankAccount(bankAccountId);

        if (bankAcc == null) {
            return -1;

        } else {
            if (type == OperationTypes.EXPENSE) {
                bankAcc.setBalance(bankAcc.getBalance() - amount);
            } else if (type == OperationTypes.INCOME) {
                bankAcc.setBalance(bankAcc.getBalance() + amount);
            }

            bankAccountsStorage.updateBankAccount(bankAcc);
        }

        var categoryId = categoriesStorage.getCategoryIdByName(categoryName);

        if (categoryId == -1) {
            var newCat = categoryFactory.createCategory(categoryName, type);
            categoriesStorage.addCategory(newCat);
            categoryId = newCat.getId();
        }

        Operation operation = operationFactory.createOperation(type, bankAccountId, amount, date, categoryId, description);

        try {
            operationsStorage.addOperation(operation);
        } catch (Exception e) {
            return -1;
        }

        return operation.getId();
    }

    public int performOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, String categoryName) {
        var bankAcc = bankAccountsStorage.getBankAccount(bankAccountId);

        if (bankAcc == null) {
            return -1;

        } else {
            if (type == OperationTypes.EXPENSE) {
                bankAcc.setBalance(bankAcc.getBalance() - amount);
            } else if (type == OperationTypes.INCOME) {
                bankAcc.setBalance(bankAcc.getBalance() + amount);
            }

            bankAccountsStorage.updateBankAccount(bankAcc);
        }

        var categoryId = categoriesStorage.getCategoryIdByName(categoryName);

        if (categoryId == -1) {
            var newCat = categoryFactory.createCategory(categoryName, type);
            categoriesStorage.addCategory(newCat);
            categoryId = newCat.getId();
        }

        Operation operation = operationFactory.createOperation(type, bankAccountId, amount, date, categoryId);

        try {
            operationsStorage.addOperation(operation);
        } catch (Exception e) {
            return -1;
        }

        return operation.getId();
    }

    public Optional<BankAccountAnalytics> getBankAccountAnalytics(int accountId, LocalDate startDate, LocalDate endDate) {
        var returnBankAccount = bankAccountsStorage.getBankAccount(accountId);

        if (returnBankAccount == null) {
            return Optional.empty();
        } else {
            var returnAnalytics = new BankAccountAnalytics(accountId, startDate, endDate);
            var accountOperations = operationsStorage
                    .fetchAccountOperations(accountId)
                    .stream()
                    .filter(x -> x.getDate().isBefore(endDate) && x.getDate().isAfter(startDate))
                    .toList();

            for (var operation : accountOperations) {
                if (operation.getType() == OperationTypes.EXPENSE) {
                    returnAnalytics.setExpensesSum(returnAnalytics.getExpensesSum() + operation.getAmount());
                    var categoryName = categoriesStorage.getCategory(operation.getCategoryId()).getName();
                    returnAnalytics.addValue(OperationTypes.EXPENSE, categoryName, operation.getAmount());
                } else if (operation.getType() == OperationTypes.INCOME) {
                    returnAnalytics.setIncomeSum(returnAnalytics.getIncomeSum() + operation.getAmount());
                    var categoryName = categoriesStorage.getCategory(operation.getCategoryId()).getName();
                    returnAnalytics.addValue(OperationTypes.INCOME, categoryName, operation.getAmount());
                }
            }

            if (returnAnalytics.valuesByCategories.get(OperationTypes.EXPENSE) == null) {
                returnAnalytics.valuesByCategories.put(OperationTypes.EXPENSE, new HashMap<>());
            } else {
                returnAnalytics.valuesByCategories
                        .get(OperationTypes.EXPENSE)
                        .forEach((key, value) -> {
                            value.set(1, value.getFirst() / returnAnalytics.getExpensesSum());
                            value.set(2, value.getFirst() / (returnAnalytics.getExpensesSum() + returnAnalytics.getIncomeSum()));
                        });
            }


            if (returnAnalytics.valuesByCategories.get(OperationTypes.INCOME) == null) {
                returnAnalytics.valuesByCategories.put(OperationTypes.INCOME, new HashMap<>());
            } else {
                returnAnalytics.valuesByCategories
                        .get(OperationTypes.INCOME)
                        .forEach((key, value) -> {
                            value.set(1, value.getFirst() / returnAnalytics.getIncomeSum());
                            value.set(2, value.getFirst() / (returnAnalytics.getExpensesSum() + returnAnalytics.getIncomeSum()));
                        });
            }

            return Optional.of(returnAnalytics);
        }
    }

    public void exportServiceInfo (String filename) {
        var exporter = exporterFactory.createExporter(filename);

        if (exporter != null) {
            this.bankAccountsStorage.export(exporter);
            this.categoriesStorage.export(exporter);
            this.operationsStorage.export(exporter);
            exporter.export();
        } else {
            throw new IllegalArgumentException("Exporter could not be created");
        }
    }

    public void importServiceInfo (String filename) {
        var importer = importerFactory.createImporter(filename);
        if (importer != null) {
            importer.importService();
        } else {
            throw new IllegalArgumentException("Importer could not be created");
        }
    }

    public double recalculateBalance(int accountID) throws IllegalStateException {
        var initialAcc = bankAccountsStorage.getBankAccount(accountID);
        List<Operation> ops = operationsStorage.fetchAccountOperations(accountID);

        double balance = 0;
        for (Operation op : ops) {
            switch (op.getType()) {
                case INCOME -> balance += op.getAmount();
                case EXPENSE -> balance -= op.getAmount();
                default -> throw new IllegalStateException("Unexpected value: " + op.getType());
            }
        }

        initialAcc.setBalance(balance);
        bankAccountsStorage.updateBankAccount(initialAcc);
        return balance;
    }
}
