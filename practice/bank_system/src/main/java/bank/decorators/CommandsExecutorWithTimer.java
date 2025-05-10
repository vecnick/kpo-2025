package bank.decorators;

import bank.interfaces.ICommand;
import bank.interfaces.ICommandExecutor;
import org.springframework.stereotype.Component;

@Component
public class CommandsExecutorWithTimer extends CommandsExecutorDecorator {

    public CommandsExecutorWithTimer(ICommandExecutor executor) {
        super(executor);
    }

    @Override
    public void run(ICommand command) {
        long start = System.nanoTime();
        executor.run(command);
        long end = System.nanoTime();
        long duration = end - start;
        System.out.println("Команда выполнена за " + duration / 1_000_000.0 + " ms");
    }
}
