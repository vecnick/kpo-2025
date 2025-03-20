package finance.commands.ImportExport.Import;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class ImportYamlCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final String fileName;

    public ImportYamlCommand(FinanceFacade financeFacade, String fileName) {
        this.financeFacade = financeFacade;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        financeFacade.yamlImport(fileName);
        System.out.println("Data was successfully imported from yaml.");
    }
}
