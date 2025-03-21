package bank;

import bank.enums.ReportFormat;
import bank.facade.BankAccountFacade;
import bank.facade.CategoryFacade;
import bank.facade.OperationFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        BankAccountFacade bankAccountFacade = context.getBean(BankAccountFacade.class);
        CategoryFacade categoryFacade = context.getBean(CategoryFacade.class);
        OperationFacade operationFacade = context.getBean(OperationFacade.class);

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
//        bankAccountFacade.addAccount("Maksim", 200);
//        bankAccountFacade.addAccount("Boris", 780);
//        bankAccountFacade.exportAccounts(ReportFormat.YAML);
        bankAccountFacade.importAccounts("report.csv");

        bankAccountFacade.getAccounts().stream().map(Object::toString).forEach(System.out::println);

    }
}