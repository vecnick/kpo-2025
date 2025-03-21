package bank;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.enums.ReportFormat;
import bank.facade.BankAccountFacade;
import bank.facade.CategoryFacade;
import bank.facade.OperationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleApp {
    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;

     public void start() throws IOException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            String command = scanner.nextLine().trim();

            switch (command) {
                // add
                case "addAccount" -> addAccount(scanner);
                case "addCategory" -> addCategory(scanner);
                case "addOperation" -> addOperation(scanner); // заранее должны быть созданы Account и Category
                // get by id
                case "getAccountById" -> getAccountById(scanner);
                case "getCategoryById" -> getCategoryById(scanner);
                case "getOperationById" -> getOperationById(scanner);
                // get all
                case "getAccounts" -> getAccounts();
                case "getCategories" -> getCategories();
                case "getOperations" -> getOperations();
                // remove by id
                case "removeAccountById" -> removeAccountById(scanner);
                case "removeCategoryById" -> removeCategoryById(scanner);
                case "removeOperationById" -> removeOperationById(scanner);
                // export
                case "exportAccounts" -> exportAccounts(scanner);
                case "exportCategories" -> exportCategories(scanner);
                case "exportOperations" -> exportOperations(scanner);
                // import
                case "importAccounts" -> importAccounts(scanner);
                case "importCategories" -> importCategories(scanner);
                case "importOperations" -> importOperations(scanner);
                // специальные функции аналитики/расчётов
                case "recalculateBalance" -> recalculateBalance(scanner);
                case "recalculateBalanceWithTimer" -> recalculateBalanceWithTimer(scanner);
                case "groupOperationsByCategoryName" -> groupOperationsByCategoryName();
                // неизвестная команда
                default -> System.out.println("Ошибка: неизвестная команда: " + command);
            }
            System.out.println("-----------");
        }

    }
    public void addAccount(Scanner scanner) {
        System.out.print("(name): ");
        String name = scanner.nextLine();

        System.out.print("(balance): ");
        double balance = Double.parseDouble(scanner.nextLine());

         bankAccountFacade.addAccount(name, balance);
    }

    public void addCategory(Scanner scanner) {
        System.out.print("(type): ");
        String typeString = scanner.nextLine();
        OperationType type = OperationType.valueOf(typeString);

        System.out.print("(name): ");
        String name = scanner.nextLine();

        categoryFacade.addCategory(type, name);
    }

    public void addOperation(Scanner scanner) {
        System.out.print("(type): ");
        String typeString = scanner.nextLine();
        OperationType type = OperationType.valueOf(typeString);

        System.out.print("(bankAccountId): ");
        int bankAccountId = scanner.nextInt();
        scanner.nextLine();
        BankAccount bankAccount = bankAccountFacade.getAccountById(bankAccountId).orElseThrow(() -> new IllegalArgumentException("Аккаунт с данным id не найден"));

        System.out.print("(amount): ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("(description): ");
        String description = scanner.nextLine();

        System.out.print("(categoryId): ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        Category category = categoryFacade.getCategoryById(categoryId).orElseThrow(() -> new IllegalArgumentException("Категория с данным id не найден"));

        operationFacade.addOperation(type, bankAccount, amount, description, category);
    }

    public void getAccountById(Scanner scanner) {
        System.out.print("(id): ");
        int id = scanner.nextInt();
        scanner.nextLine();

         BankAccount bankAccount = bankAccountFacade.getAccountById(id).orElseThrow(() -> new IllegalArgumentException("Аккаунт с данным id не найден"));
         System.out.println(bankAccount.toString());
    }

    public void getCategoryById(Scanner scanner) {
        System.out.print("(id): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Category category = categoryFacade.getCategoryById(id).orElseThrow(() -> new IllegalArgumentException("Категория с данным id не найден"));
        System.out.println(category.toString());
     }

    public void getOperationById(Scanner scanner) {
        System.out.print("(id): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Operation operation = operationFacade.getOperationById(id).orElseThrow(() -> new IllegalArgumentException("Операция с данным id не найден"));
        System.out.println(operation.toString());
    }

    public void getAccounts() {
        List<BankAccount> accounts = bankAccountFacade.getAccounts();
        accounts.stream().map(Object::toString).forEach(System.out::println);
    }

    public void getCategories() {
        List<Category> categories = categoryFacade.getCategories();
        categories.stream().map(Object::toString).forEach(System.out::println);
    }

    public void getOperations() {
        List<Operation> operations = operationFacade.getOperations();
        operations.stream().map(Object::toString).forEach(System.out::println);
    }

    public void removeAccountById(Scanner scanner) {
        System.out.print("(id): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        bankAccountFacade.removeAccountById(id);
    }
    public void removeCategoryById(Scanner scanner) {
        System.out.print("(id): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        categoryFacade.removeCategoryById(id);
    }
    public void removeOperationById(Scanner scanner) {
        System.out.print("(id): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        operationFacade.removeOperationById(id);
    }

    public void exportAccounts(Scanner scanner) throws IOException, IllegalAccessException {
        System.out.print("(format): ");
        String formatString = scanner.nextLine();
        ReportFormat format = ReportFormat.valueOf(formatString);

        bankAccountFacade.exportAccounts(format);
    }

    public void exportCategories(Scanner scanner) throws IOException, IllegalAccessException {
        System.out.print("(format): ");
        String formatString = scanner.nextLine();
        ReportFormat format = ReportFormat.valueOf(formatString);

        categoryFacade.exportCategories(format);
    }

    public void exportOperations(Scanner scanner) throws IOException, IllegalAccessException {
        System.out.print("(format): ");
        String formatString = scanner.nextLine();
        ReportFormat format = ReportFormat.valueOf(formatString);

        operationFacade.exportOperations(format);
    }

    public void importAccounts(Scanner scanner) throws IOException {
        System.out.print("(filename): ");
        String filename = scanner.nextLine();
        bankAccountFacade.importAccounts(filename);
    }

    public void importCategories(Scanner scanner) throws IOException {
        System.out.print("(filename): ");
        String filename = scanner.nextLine();
        categoryFacade.importCategories(filename);
    }

    public void importOperations(Scanner scanner) throws IOException {
        System.out.print("(filename): ");
        String filename = scanner.nextLine();
        operationFacade.importOperations(filename);
    }

    public void recalculateBalance(Scanner scanner) {
        System.out.print("(bankAccountId): ");
        int bankAccountId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("(dateFrom): ");
        String dateFrom = scanner.nextLine();

        System.out.print("(dateTo): ");
        String dateTo = scanner.nextLine();

         bankAccountFacade.recalculateBalance(bankAccountId, dateFrom, dateTo);
    }

    public void recalculateBalanceWithTimer(Scanner scanner) {
        System.out.print("(bankAccountId): ");
        int bankAccountId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("(dateFrom): ");
        String dateFrom = scanner.nextLine();

        System.out.print("(dateTo): ");
        String dateTo = scanner.nextLine();

        bankAccountFacade.recalculateBalanceWithTimer(bankAccountId, dateFrom, dateTo);
    }

    public void groupOperationsByCategoryName() {
        Map<String, List<Operation>> operationsByCategory = operationFacade.groupOperationsByCategoryName();
        operationsByCategory.forEach((category, operations) -> {
            System.out.println(category + ":"); // название категории
            operations.forEach(op -> System.out.println(op.toString())); // операции
            System.out.println(); // пустая строка между категориями
        });
    }
}