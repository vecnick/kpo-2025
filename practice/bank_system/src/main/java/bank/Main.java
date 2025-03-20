package bank;

import bank.enums.ReportFormat;
import bank.facade.BankAccountFacade;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
//        // ФАБРИКИ
//        BankAccountFactory bankAccountFactory = new BankAccountFactory();
//        CategoryFactory categoryFactory = new CategoryFactory();
//        OperationFactory operationFactory = new OperationFactory();
//
//        // ХРАНИЛИЩА
//        BankAccountsStorage bankAccountsStorage = new BankAccountsStorage();
//        CategoryStorage categoryStorage = new CategoryStorage();
//        OperationsStorage operationsStorage = new OperationsStorage();
//
//        // СЕРВИСЫ
//        BankAccountService bankAccountService = new BankAccountService(bankAccountsStorage);
//        CategoryService categoryService = new CategoryService(categoryStorage);
//        OperationService operationService = new OperationService(operationsStorage);
//
//        // ДОБАВЛЕНИЕ АККАУНТОВ
//        bankAccountService.addAccount(bankAccountFactory, "Maksim", 200);
//        bankAccountService.addAccount(bankAccountFactory, "Boris", 780);
//
//        // ДОБАВЛЕНИЕ КАТЕГОРИЙ
//        categoryService.addCategory(categoryFactory, OperationType.INCOME, "supermarket");
//        categoryService.addCategory(categoryFactory, OperationType.OUTCOME, "gym");
//        categoryService.addCategory(categoryFactory, OperationType.INCOME, "swimming pool");
//        categoryService.addCategory(categoryFactory, OperationType.OUTCOME, "supermarket");
//
//        System.out.println(categoryService.getOperationById(1).toString());
        BankAccountFacade bankAccountFacade = new BankAccountFacade();
        bankAccountFacade.addAccount("Maksim", 200);
        bankAccountFacade.addAccount("Boris", 780);
        bankAccountFacade.exportAccounts(ReportFormat.CSV);

    }
}