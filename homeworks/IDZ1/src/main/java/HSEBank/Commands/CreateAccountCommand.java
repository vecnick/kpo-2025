package HSEBank.Commands;

import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;

public class CreateAccountCommand implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public CreateAccountCommand(BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            System.out.println("You need to specify the name of new account");
        } else {
            var res = bankAccountService.createBankAccount(args[0]);

            if (res == -1) {
                System.out.println("Account creation failed");
            } else {
                System.out.println("Successfully created new account with id " + res);
            }
        }
    }
}
