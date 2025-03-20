package HSEBank.Commands;

import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;


public class DeleteBankAccountCommand implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public DeleteBankAccountCommand (BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            System.out.println("You need to specify the id of account to delete");
        } else {
            var accId = -1;
            try {
                accId = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID");
                return;
            }
            var res = bankAccountService.deleteBankAccount(accId);

            if (res == -1) {
                System.out.println("Bank account with ID " + accId + " does not exist");
            } else {
                System.out.println("Bank account with ID " + res + " has been deleted");
            }
        }
    }
}

