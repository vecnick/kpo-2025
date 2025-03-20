package finance.services;

import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsService {
    public double getSummaryForAccount(List<Operation> operations, int id, LocalDate startDate, LocalDate endDate) {
        double summary = 0;

        for (Operation operation : operations) {
            if (operation.getDate().isBefore(startDate) ||
                    operation.getDate().isAfter(endDate) ||
                    operation.getBankAccountId() != id) {
                continue;
            }

            summary += switch (operation.getType()) {
                case 0 -> operation.getAmount();
                case 1 -> -operation.getAmount();
                default -> throw new IllegalArgumentException("Unknown type of operation");
            };
        }

        return summary;
    }

    public double getSummaryForAll(List<Operation> operations, LocalDate startDate, LocalDate endDate) {
        double summary = 0;

        for (Operation operation : operations) {
            if (operation.getDate().isBefore(startDate) || operation.getDate().isAfter(endDate)) {
                continue;
            }

            summary += switch (operation.getType()) {
                case 0 -> operation.getAmount();
                case 1 -> -operation.getAmount();
                default -> throw new IllegalArgumentException("Unknown type of operation");
            };
        }

        return summary;
    }

    public Map<Category, List<Operation>> groupByCategories(List<Category> categories, List<Operation> operations) {
        Map<Integer, Category> categoryIdToCategory = categories.stream()
                .collect(Collectors.toMap(Category::getId, category -> category));

        return operations.stream()
                .filter(operation -> categoryIdToCategory.containsKey(operation.getCategoryId()))
                .collect(Collectors.groupingBy(
                        operation -> categoryIdToCategory.get(operation.getCategoryId()),
                        HashMap::new,
                        Collectors.toList()
                ));
    }

    public Map<BankAccount, List<Operation>> groupByAccounts(List<BankAccount> accounts, List<Operation> operations) {
        Map<Integer, BankAccount> accountIdToAccount = accounts.stream()
                .collect(Collectors.toMap(BankAccount::getId, account -> account));

        return operations.stream()
                .filter(operation -> accountIdToAccount.containsKey(operation.getCategoryId()))
                .collect(Collectors.groupingBy(
                        operation -> accountIdToAccount.get(operation.getCategoryId()),
                        HashMap::new,
                        Collectors.toList()
                ));
    }
}
