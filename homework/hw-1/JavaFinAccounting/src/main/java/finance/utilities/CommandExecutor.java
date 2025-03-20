package finance.utilities;

import finance.interfaces.ICommand;

public class CommandExecutor {
    public void executeCommand(ICommand command) {
        command.execute();
    }
}
