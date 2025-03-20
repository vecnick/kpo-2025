package finance.commands.ImportExport.Import;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class ImportCsvCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final String fileName;

    public ImportCsvCommand(FinanceFacade financeFacade, String fileName) {
        this.financeFacade = financeFacade;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        financeFacade.csvImport(fileName);
        System.out.println("Data was successfully imported from csv.");
    }
}
