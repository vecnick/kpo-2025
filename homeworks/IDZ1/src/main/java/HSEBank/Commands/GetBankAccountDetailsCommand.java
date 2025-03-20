package HSEBank.Commands;

import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;


public class GetBankAccountDetailsCommand implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public GetBankAccountDetailsCommand(BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            System.out.println("You need to enter the account id");
        } else {
            var accId = -1;
            try {
                accId = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID");
                return;
            }
            var info = bankAccountService.getBankAccountDetails(accId);

            if (info.isEmpty()) {
                System.out.println("Account with id " + accId + " does not exist");
            } else {
                System.out.println("Account with id " + accId + " found");
                System.out.println("Account name: " + info.get().getAccountName());
                System.out.println("Account balance: " + info.get().getBalance());
            }
        }
    }
}