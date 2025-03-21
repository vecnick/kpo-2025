package bank.decorators;

import bank.interfaces.ICommand;
import bank.interfaces.ICommandExecutor;

abstract class CommandsExecutorDecorator implements ICommandExecutor {
    protected final ICommandExecutor executor;

    public CommandsExecutorDecorator(ICommandExecutor executor) {
        this.executor = executor;
    }
}
