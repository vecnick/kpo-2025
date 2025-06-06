package bank.commands;

import bank.interfaces.ICommand;
import bank.interfaces.ICommandExecutor;
import org.springframework.stereotype.Component;

@Component
public class CommandsExecutor implements ICommandExecutor {
    public void run(ICommand command) {
        command.execute();
    }
}
