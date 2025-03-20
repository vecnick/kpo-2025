package finance.commands.ImportExport.Export;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;

public class ExportJsonCommand implements ICommand {
    private FinanceFacade financeFacade;

    private final String accountsFileName;

    private final String categoriesFileName;

    private final String operationsFileName;

    public ExportJsonCommand(FinanceFacade financeFacade, String accountsFileName,
                            String categoriesFileName,
                            String operationsFileName) {
        this.financeFacade = financeFacade;
        this.accountsFileName = accountsFileName;
        this.categoriesFileName = categoriesFileName;
        this.operationsFileName = operationsFileName;
    }

    @Override
    public void execute() {
        financeFacade.jsonExport(accountsFileName, categoriesFileName, operationsFileName);
        System.out.println("Data was successfully exported to json.");
    }
}
