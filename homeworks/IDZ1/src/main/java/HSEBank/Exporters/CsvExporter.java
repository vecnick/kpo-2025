package HSEBank.Exporters;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.Operations.Operation;
import HSEBank.Interfaces.ExporterI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CsvExporter implements ExporterI {
    FileWriter fileWriter;

    public CsvExporter(String fileName) {
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportOperations(List<Operation> operations) throws IOException {
        fileWriter.write(String.format("%s,%d%n", "Operations", operations.size()));
        for (Operation operation: operations) {
            fileWriter.write(String.format(Locale.US, "%d,%s,%d,%s,%d,%s,%s%n",
                    operation.getId(),
                    operation.getType().getDescription(),
                    operation.getBankAccountId(),
                    String.format(Locale.US,"%.2f", operation.getAmount()),
                    operation.getCategoryId(),
                    operation.getDate(),
                    operation.getDescription()
            ));
        }
    }

    @Override
    public void exportAccounts(List<BankAccount> accounts) throws IOException {
        fileWriter.write(String.format("%s,%d%n", "Accounts", accounts.size()));
        for (BankAccount account: accounts) {
            fileWriter.write(String.format(Locale.US,"%d,%s,%s%n",
                    account.getId(),
                    account.getAccountName(),
                    String.format(Locale.US,"%.2f", account.getBalance())
            ));
        }
    }

    @Override
    public void exportCategories(List<Category> categories) throws IOException {
        fileWriter.write(String.format("%s,%d%n", "Categories", categories.size()));
        for (Category category: categories) {
            fileWriter.write(String.format(Locale.US,"%d,%s,%s%n",
                    category.getId(),
                    category.getName(),
                    category.getOperationType().getDescription()
            ));
        }
    }

    @Override
    public void export() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
