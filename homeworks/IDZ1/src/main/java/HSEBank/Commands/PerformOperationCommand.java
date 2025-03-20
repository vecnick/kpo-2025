package HSEBank.Commands;

import HSEBank.Enums.OperationTypes;
import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;

import java.time.LocalDate;


public class PerformOperationCommand implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public PerformOperationCommand(BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length < 5) {
            System.out.println("You need to enter operation type, account id, the amount of money, the date, category name");
        } else {
            var type = OperationTypes.EXPENSE;

            try {
                type = OperationTypes.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid operation type");
            }

            var accId = -1;
            try {
                accId = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID");
                return;
            }

            var amount = 0.0;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount of money");
                return;
            }

            var opDate = LocalDate.now();
            try {
                opDate = LocalDate.parse(args[3]);
            } catch (Exception e) {
                System.out.println("Please enter a valid date in format: YYYY-MM-DD");
                return;
            }

            var res = -1;
            if (args.length == 6) {
                res = bankAccountService.performOperation(type, accId, amount, opDate, args[4], args[5]);
            } else {
                res = bankAccountService.performOperation(type, accId, amount, opDate, args[4]);
            }

            if (res == -1) {
                System.out.println("Operation failed");
            } else {
                System.out.println("Operation successful. Operation ID: " + res);
            }

        }
    }
}
