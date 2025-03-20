package HSEBank.Commands;

import HSEBank.Enums.OperationTypes;
import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;

import java.time.LocalDate;

public class GetBankAccountAnalyticsCommand implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public GetBankAccountAnalyticsCommand(BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length < 3) {
            System.out.println("You need to enter the account id, startDate and endDate");
        } else {
            var accId = -1;
            try {
                accId = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID");
                return;
            }

            var startDate = LocalDate.now();
            var endDate = LocalDate.now();

            try {
                startDate = LocalDate.parse(args[1]);
            } catch (Exception e) {
                System.out.println("Please enter a valid start date in format: YYYY-MM-DD");
                return;
            }

            try {
                endDate = LocalDate.parse(args[2]);
            } catch (Exception e) {
                System.out.println("Please enter a valid end date in format: YYYY-MM-DD");
                return;
            }


            var info = bankAccountService.getBankAccountAnalytics(accId, startDate, endDate);

            if (info.isEmpty()) {
                System.out.println("Account with id " + accId + " does not exist");
            } else {
                var stats = info.get();
                System.out.println("Account with id " + stats.getBankAccountId() + " found");
                System.out.println("Statistics from " + stats.getStartDate() + " to " + stats.getEndDate());
                System.out.println("Summary income: " + stats.getIncomeSum());
                System.out.println("Summary expenses: " + stats.getExpensesSum());

                System.out.println("Incomes categories:");
                System.out.println("\t" + "Category;" + " " + "Amount;" + " " + "Percentage within type;" + " " + "Percentage within whole amount;");
                var incomes = stats.valuesByCategories.get(OperationTypes.INCOME);
                if (incomes != null) {
                    incomes.forEach((key, value) -> {
                        System.out.println("\t" + key + ": " + value.getFirst() + " " + String.format("%.2f", value.get(1) * 100) + " " + String.format("%.2f", value.get(2) * 100));
                    });
                } else {
                    System.out.println("No incomes found");
                }

                System.out.println("Expenses categories:");
                System.out.println("\t" + "Category;" + " " + "Amount;" + " " + "Percentage within type;" + " " + "Percentage within whole amount;");
                var expenses = stats.valuesByCategories.get(OperationTypes.EXPENSE);
                if (expenses != null) {
                    expenses.forEach((key, value) -> {
                        System.out.println("\t" + key + ": " + value.getFirst() + " " + String.format("%.2f", value.get(1) * 100) + " " + String.format("%.2f", value.get(2) * 100));
                    });
                } else {
                    System.out.println("No expenses found");
                }
            }
        }
    }
}
