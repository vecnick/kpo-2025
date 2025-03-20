package finance.utilities;

import finance.commands.Analytics.GroupByAccountsCommand;
import finance.commands.Analytics.GroupByCategoriesCommand;
import finance.commands.Analytics.SummaryAccountCommand;
import finance.commands.Analytics.SummaryAllCommand;
import finance.commands.BankAccount.CreateAccountCommand;
import finance.commands.BankAccount.DeleteAccountCommand;
import finance.commands.BankAccount.GetAccountsCommand;
import finance.commands.BankAccount.UpdateAccountCommand;
import finance.commands.Category.CreateCategoryCommand;
import finance.commands.Category.DeleteCategoryCommand;
import finance.commands.Category.GetCategoriesCommand;
import finance.commands.Category.UpdateCategoryCommand;
import finance.commands.ImportExport.Export.ExportCsvCommand;
import finance.commands.ImportExport.Export.ExportJsonCommand;
import finance.commands.ImportExport.Export.ExportYamlCommand;
import finance.commands.ImportExport.Import.ImportCsvCommand;
import finance.commands.ImportExport.Import.ImportJsonCommand;
import finance.commands.ImportExport.Import.ImportYamlCommand;
import finance.commands.Operation.CreateOperationCommand;
import finance.commands.Operation.DeleteOperationCommand;
import finance.commands.Operation.GetOperationsCommand;
import finance.commands.Operation.UpdateOperationCommand;
import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

import java.time.LocalDate;

import static finance.enums.OperationType.EXPENSES;
import static finance.enums.OperationType.INCOME;

public class CommandParser {
    private FinanceFacade financeFacade;

    public CommandParser(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    public ICommand parse(String input) {
        String[] parts = input.split(" ");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid command");
        }

        String action = parts[0];
        String entity = parts[1];

        int id;
        int type;
        double amount;
        LocalDate date;
        int bankAccountId;
        int categoryId;
        StringBuilder description = new StringBuilder();
        String name;
        double balance;
        LocalDate startDate;
        LocalDate endDate;
        String accountsFileName;
        String categoriesFileName;
        String operationsFileName;
        String fileName;

        switch (action.toLowerCase() + " " + entity.toLowerCase()) {
            case "create account":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Expected 2 arguments, got " + (parts.length - 2));
                }

                name = parts[2];
                balance = Double.parseDouble(parts[3]);
                return new CreateAccountCommand(financeFacade, name, balance);

            case "update account":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Expected 3 arguments, got " + (parts.length - 2));
                }

                id = Integer.parseInt(parts[2]);
                name = parts[3];
                balance = Double.parseDouble(parts[4]);
                return new UpdateAccountCommand(financeFacade, id, name, balance);

            case "delete account":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 1 arguments, got " + (parts.length - 2));
                }

                id = Integer.parseInt(parts[2]);
                return new DeleteAccountCommand(financeFacade, id);

            case "create category":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Expected 2 arguments, got " + (parts.length - 2));
                }

                type = switch (parts[2].toLowerCase()) {
                    case "i", "income" -> INCOME.ordinal();
                    case "e", "expenses" -> EXPENSES.ordinal();
                    default -> throw new IllegalArgumentException("Invalid category type.");
                };
                name = parts[3];

                return new CreateCategoryCommand(financeFacade, type, name);

            case "update category":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Expected 2 arguments, got " + (parts.length - 2));
                }

                id = Integer.parseInt(parts[2]);
                name = parts[3];

                return new UpdateCategoryCommand(financeFacade, id, name);

            case "delete category":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 3 arguments, got " + (parts.length - 2));
                }

                id = Integer.parseInt(parts[2]);

                return new DeleteCategoryCommand(financeFacade, id);

            case "create operation":
                if (parts.length < 7) {
                    throw new IllegalArgumentException("Expected 5 or 6 arguments, got " + (parts.length - 2));
                }

                type = switch (parts[2].toLowerCase()) {
                    case "i", "income" -> INCOME.ordinal();
                    case "e", "expenses" -> EXPENSES.ordinal();
                    default -> throw new IllegalArgumentException("Invalid category type.");
                };
                amount = Double.parseDouble(parts[3]);
                date = parts[4].equalsIgnoreCase("now") ? LocalDate.now() : LocalDate.parse(parts[4]);
                bankAccountId = Integer.parseInt(parts[5]);
                categoryId = Integer.parseInt(parts[6]);

                if (parts.length >= 8) {
                    for (int i = 7; i < parts.length; i++) {
                        description.append(parts[i]).append(" ");
                    }

                    return new CreateOperationCommand(financeFacade, type, amount, date,
                            bankAccountId, categoryId, description.toString());
                }

                return new CreateOperationCommand(financeFacade, type, amount, date,
                        bankAccountId, categoryId, "");

            case "update operation":
                if (parts.length < 8) {
                    throw new IllegalArgumentException("Expected 6 or 7 arguments, got " + (parts.length - 2));
                }

                id = Integer.parseInt(parts[2]);
                type = switch (parts[3].toLowerCase()) {
                    case "i", "income" -> INCOME.ordinal();
                    case "e", "expenses" -> EXPENSES.ordinal();
                    default -> throw new IllegalArgumentException("Invalid category type.");
                };
                amount = Double.parseDouble(parts[4]);
                date = parts[5].equalsIgnoreCase("now") ? LocalDate.now() : LocalDate.parse(parts[4]);
                bankAccountId = Integer.parseInt(parts[6]);
                categoryId = Integer.parseInt(parts[7]);

                if (parts.length >= 9) {
                    for (int i = 8; i < parts.length; i++) {
                        description.append(parts[i]).append(" ");
                    }

                    return new UpdateOperationCommand(financeFacade, id, type, amount, date,
                            bankAccountId, categoryId, description.toString());
                }

                return new UpdateOperationCommand(financeFacade, id, type, amount, date,
                        bankAccountId, categoryId, "");

            case "delete operation":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 3 arguments, got " + (parts.length - 2));
                }

                id = Integer.parseInt(parts[2]);

                return new DeleteOperationCommand(financeFacade, id);

            case "get accounts":
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Expected 0 arguments, got " + (parts.length - 2));
                }

                return new GetAccountsCommand(financeFacade);

            case "get categories":
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Expected 0 arguments, got " + (parts.length - 2));
                }

                return new GetCategoriesCommand(financeFacade);

            case "get operations":
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Expected 0 arguments, got " + (parts.length - 2));
                }

                return new GetOperationsCommand(financeFacade);

            case "analytics summary":
                if (parts.length != 5 && parts.length != 6) {
                    throw new IllegalArgumentException("Expected 3 or 4 arguments, got " + (parts.length - 2));
                }

                switch (parts[2].toLowerCase()) {
                    case "account":
                        id = Integer.parseInt(parts[3]);
                        startDate = parts[4].equalsIgnoreCase("now") ?
                                LocalDate.now() :
                                LocalDate.parse(parts[4]);
                        endDate = parts[5].equalsIgnoreCase("now") ?
                                LocalDate.now() :
                                LocalDate.parse(parts[4]);

                        return new SummaryAccountCommand(financeFacade, id, startDate, endDate);

                    case "all":
                        startDate = parts[3].equalsIgnoreCase("now") ?
                                LocalDate.now() :
                                LocalDate.parse(parts[3]);
                        endDate = parts[4].equalsIgnoreCase("now") ?
                                LocalDate.now() :
                                LocalDate.parse(parts[4]);

                        return new SummaryAllCommand(financeFacade, startDate, endDate);

                    default:
                        throw new IllegalArgumentException("Expected 'account' or 'all', got " +
                                "'" + parts[2].toLowerCase() + "'");
                }

            case "group by":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 1 arguments, got " + (parts.length - 2));
                }

                return switch (parts[2].toLowerCase()) {
                    case "categories" -> new GroupByCategoriesCommand(financeFacade);
                    case "accounts" -> new GroupByAccountsCommand(financeFacade);
                    default -> throw new IllegalArgumentException("Expected 'categories' or 'accounts', got " +
                            "'" + parts[2].toLowerCase() + "'");
                };

            case "export csv":
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Expected 3 arguments, got " + (parts.length - 2));
                }

                accountsFileName = parts[2];
                categoriesFileName = parts[3];
                operationsFileName = parts[4];

                return new ExportCsvCommand(financeFacade, accountsFileName, categoriesFileName, operationsFileName);

            case "export yaml":
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Expected 3 arguments, got " + (parts.length - 2));
                }

                accountsFileName = parts[2];
                categoriesFileName = parts[3];
                operationsFileName = parts[4];

                return new ExportYamlCommand(financeFacade, accountsFileName, categoriesFileName, operationsFileName);

            case "export json":
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Expected 3 arguments, got " + (parts.length - 2));
                }

                accountsFileName = parts[2];
                categoriesFileName = parts[3];
                operationsFileName = parts[4];

                return new ExportJsonCommand(financeFacade, accountsFileName, categoriesFileName, operationsFileName);

            case "import csv":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 1 arguments, got " + (parts.length - 2));
                }

                fileName = parts[2];

                return new ImportCsvCommand(financeFacade, fileName);

            case "import yaml":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 1 arguments, got " + (parts.length - 2));
                }

                fileName = parts[2];

                return new ImportYamlCommand(financeFacade, fileName);

            case "import json":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Expected 1 arguments, got " + (parts.length - 2));
                }

                fileName = parts[2];

                return new ImportJsonCommand(financeFacade, fileName);

            default:
                throw new IllegalArgumentException("Unknown command: " + input);
        }
    }
}