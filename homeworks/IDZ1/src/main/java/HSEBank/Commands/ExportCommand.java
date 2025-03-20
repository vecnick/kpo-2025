package HSEBank.Commands;

import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Interfaces.Executable;
import HSEBank.Services.BankAccountService;
import lombok.Getter;

public class ExportCommand  implements Executable {
    private final BankAccountServiceI bankAccountService;
    @Getter
    private final String[] args;

    public ExportCommand  (BankAccountServiceI bankAccountService, String[] args) {
        this.bankAccountService = bankAccountService;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            System.out.println("You need to specify the name of the file to export to.");
        } else {

            try {
                bankAccountService.exportServiceInfo(args[0]);
                System.out.println("Successfully exported to " + args[0]);
            } catch (Exception e) {
                System.out.println("An error occurred while exporting to " + args[0]);
            }
        }
    }
}