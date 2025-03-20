package finance.commands.ImportExport.Import;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class ImportJsonCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final String fileName;

    public ImportJsonCommand(FinanceFacade financeFacade, String fileName) {
        this.financeFacade = financeFacade;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        financeFacade.jsonImport(fileName);
        System.out.println("Data was successfully imported from json.");
    }
}
