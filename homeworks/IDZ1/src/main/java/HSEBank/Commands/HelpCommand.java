package HSEBank.Commands;

import HSEBank.Interfaces.Executable;

public class HelpCommand implements Executable {
    public HelpCommand() {}

    @Override
    public void execute() {
        System.out.println("This is a Bank account application. Available commands:\n" +
                        "- create-account - creates account with given name (Name)\n" +
                        "- perform-operation - creates operation with given parameters (type, account id, amount, date(yyyy-MM-dd), categoryName, description(optional)) Example: perform-operation expense 0 1000 2007-12-03 Food dinner\n" +
                        "- get-details - prints detailed description of account with certain id (id)\n" +
                        "- recalculate-balance - recalculates the balance of an account with given id by iterating through all the operations\n" +
                        "- get-analytics - prints data about operations over certain account that were performed from startDate to endDate (id, startDate, endDate(yyyy-MM-dd)). Example: 0 2006-01-01 2008-01-01\n" +
                        "- export - exports all the data from storages to a file with given name. The information may be then restored from the file. Example: export a.yaml\n" +
                        "- import - imports all the data from the given file to the service. Example: import a.json\n" +
                        "- exit - shutdown the app\n" +
                        "- help - prints available commands description");
    }
}

