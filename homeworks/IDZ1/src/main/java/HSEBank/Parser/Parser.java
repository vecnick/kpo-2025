package HSEBank.Parser;

import HSEBank.Commands.*;
import HSEBank.Decorators.TimeMeasurerCommandDecorator;
import HSEBank.Interfaces.BankAccountServiceI;
import HSEBank.Services.BankAccountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Component
@Getter
public class Parser {
    private final BankAccountServiceI bankAccountService;

    @Autowired
    public Parser(BankAccountServiceI bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public void parseCommandLine(String[] commands) {
        if (commands.length == 0) {
            return;
        }

        String[] argsForCommand = Arrays.copyOfRange(commands, 1, commands.length);

        var isMeasurementRequired = false;
        if (argsForCommand.length > 0 && argsForCommand[argsForCommand.length - 1].equals("--time")) {
            isMeasurementRequired = true;
            argsForCommand = Arrays.copyOfRange(argsForCommand, 0, argsForCommand.length - 1);
        }

        switch (commands[0].toLowerCase()) {
            case "help" -> new HelpCommand().execute();
            case "create-account" -> new TimeMeasurerCommandDecorator(new CreateAccountCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "delete-account" -> new TimeMeasurerCommandDecorator(new DeleteBankAccountCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "get-analytics" -> new TimeMeasurerCommandDecorator(new GetBankAccountAnalyticsCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "get-details" -> new TimeMeasurerCommandDecorator(new GetBankAccountDetailsCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "recalculate-balance" -> new TimeMeasurerCommandDecorator(new RecalculateBalanceCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "perform-operation" -> new TimeMeasurerCommandDecorator(new PerformOperationCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "export" -> new TimeMeasurerCommandDecorator(new ExportCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            case "import" -> new TimeMeasurerCommandDecorator(new ImportCommand(bankAccountService, argsForCommand), isMeasurementRequired).execute();
            default -> System.out.println("Unknown command: " + commands[0]);
        }
    }
}
