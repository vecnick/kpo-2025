package HSEBank.Commands;

import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;


public class ImportCommand implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public ImportCommand (BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            System.out.println("You need to specify the name of the file to import from.");
        } else {
            try {
                bankAccountService.importServiceInfo(args[0]);
                System.out.println("Successfully imported from file " + args[0]);
            } catch (Exception e) {
                System.out.println("An error occurred while importing from " + args[0] + ": " + e.getMessage());
            }
        }
    }
}