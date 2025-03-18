package bank;

import bank.Factories.BankAccountFactory;
import bank.Factories.CategoryFactory;
import bank.Factories.OperationFactory;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.enums.OperationType;
import bank.services.BankAccountService;
import bank.services.CategoryService;
import bank.services.OperationService;
import bank.storages.BankAccountsStorage;
import bank.storages.CategoryStorage;
import bank.storages.OperationsStorage;

import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // ФАБРИКИ
        BankAccountFactory bankAccountFactory = new BankAccountFactory();
        CategoryFactory categoryFactory = new CategoryFactory();
        OperationFactory operationFactory = new OperationFactory();

        // ХРАНИЛИЩА
        BankAccountsStorage bankAccountsStorage = new BankAccountsStorage();
        CategoryStorage categoryStorage = new CategoryStorage();
        OperationsStorage operationsStorage = new OperationsStorage();

        // СЕРВИСЫ
        BankAccountService bankAccountService = new BankAccountService(bankAccountsStorage);
        CategoryService categoryService = new CategoryService(categoryStorage);
        OperationService operationService = new OperationService(operationsStorage);

        // ДОБАВЛЕНИЕ АККАУНТОВ
        bankAccountService.addAccount(bankAccountFactory, "Maksim", 200);
        bankAccountService.addAccount(bankAccountFactory, "Boris", 780);

        // ДОБАВЛЕНИЕ КАТЕГОРИЙ
        categoryService.addCategory(categoryFactory, OperationType.INCOME, "supermarket");
        categoryService.addCategory(categoryFactory, OperationType.OUTCOME, "gym");
        categoryService.addCategory(categoryFactory, OperationType.INCOME, "swimming pool");
        categoryService.addCategory(categoryFactory, OperationType.OUTCOME, "supermarket");

    }
}