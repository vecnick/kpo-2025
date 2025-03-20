package HSEBank.Decorators;

import HSEBank.Interfaces.Executable;

public class TimeMeasurerCommandDecorator extends CommandDecorator {
    private Executable commandLineCommand;
    private boolean isMeasurementRequired;

    public TimeMeasurerCommandDecorator(Executable commandLineCommand, boolean isMeasurementRequired) {
        super(commandLineCommand);
        this.commandLineCommand = commandLineCommand;
        this.isMeasurementRequired = isMeasurementRequired;
    }

    @Override
    public void execute() {
        if (isMeasurementRequired) {
            var startTime = System.nanoTime();
            this.commandLineCommand.execute();
            var endTime = System.nanoTime();
            System.out.println("This user scenario took " + (endTime - startTime) / 1000000.0 + " ms to execute");
        } else {
            this.commandLineCommand.execute();
        }
    }
}
