package HSEBank.Importers.ServiceImport;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.Operations.Operation;
import HSEBank.Enums.OperationTypes;
import HSEBank.Services.BankAccountService;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.LocalDate;

public class CsvImporter extends AbstractServiceImporter{
    public CsvImporter(WeakReference<BankAccountService> bankAccountService, String fileName) {
        super(bankAccountService, fileName);
    }

    @Override
    public void parseFile() throws IOException {
        var nextStr = "";
        while ((nextStr = reader.readLine()) != null) {
            String[] values = nextStr.split(",");

            switch (values[0]) {
                case "Accounts":
                    for (int i = 0; i < Integer.parseInt(values[1]); i++ ) {
                        nextStr = reader.readLine();
                        String[] account = nextStr.split(",");
                        var accId = Integer.parseInt(account[0]);
                        var newAcc = new BankAccount(accId, account[1]);
                        newAcc.setBalance(Double.parseDouble(account[2]));
                        this.bankAccounts.add(newAcc);
                    }
                    break;
                case "Categories":
                    for (int i = 0; i < Integer.parseInt(values[1]); i++ ) {
                        nextStr = reader.readLine();
                        String[] categories = nextStr.split(",");
                        var accId = Integer.parseInt(categories[0]);
                        var newCat = new Category(accId, categories[1], OperationTypes.valueOf(categories[2]));
                        this.categories.add(newCat);
                    }
                    break;
                case "Operations":
                    for (int i = 0; i < Integer.parseInt(values[1]); i++ ) {
                        nextStr = reader.readLine();
                        String[] operation = nextStr.split(",");
                        var opId = Integer.parseInt(operation[0]);
                        var accId = Integer.parseInt(operation[2]);
                        var amount = Double.parseDouble(operation[3]);
                        var catId = Integer.parseInt(operation[4]);
                        var date = LocalDate.parse(operation[5]);
                        var newOp = new Operation(opId, OperationTypes.valueOf(operation[1]), accId, amount, date, operation[6], catId);
                        this.operations.add(newOp);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}