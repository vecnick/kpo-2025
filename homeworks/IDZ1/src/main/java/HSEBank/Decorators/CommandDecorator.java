package HSEBank.Decorators;

import HSEBank.Interfaces.Executable;

public abstract class CommandDecorator {
    private Executable commandLineCommand;
    public CommandDecorator(Executable commandLineCommand) {
        this.commandLineCommand = commandLineCommand;
    }

    public abstract void execute();
}
