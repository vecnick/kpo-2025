package finance.data.Export;

import com.opencsv.CSVWriter;
import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;
import finance.interfaces.IExportVisitor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvExportVisitor implements IExportVisitor {
    private final String basePath;

    public CsvExportVisitor(String basePath) {
        this.basePath = basePath;
        createExportDirectory();
    }

    private void createExportDirectory() {
        try {
            Files.createDirectories(Paths.get(basePath));
        } catch (IOException e) {
            throw new RuntimeException("Did not manage to create directory " + basePath, e);
        }
    }

    private void exportBankAccountToCsv(BankAccount account, String filePath, boolean append) {
        boolean fileExists = Files.exists(Paths.get(filePath));

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, append))) {
            if (!fileExists || !append) {
                String[] header = {"ID", "Name", "Balance"};
                writer.writeNext(header);
            }

            String[] data = {
                    String.valueOf(account.getId()),
                    account.getName(),
                    String.valueOf(account.getBalance())
            };
            writer.writeNext(data);
        } catch (IOException e) {
            throw new RuntimeException("CSV Export Error " + filePath, e);
        }
    }

    private void exportCategoriesToCsv(Category category, String filePath, boolean append) {
        boolean fileExists = Files.exists(Paths.get(filePath));

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, append))) {
            if (!fileExists || !append) {
                String[] header = {"ID", "Type", "Name"};
                writer.writeNext(header);
            }

            String[] data = {
                    String.valueOf(category.getId()),
                    category.getType() == 0 ? "Income" : "Expenses",
                    category.getName()
            };
            writer.writeNext(data);
        } catch (IOException e) {
            throw new RuntimeException("CSV Export Error " + filePath, e);
        }
    }

    private void exportOperationsToCsv(Operation operation, String filePath, boolean append) {
        boolean fileExists = Files.exists(Paths.get(filePath));

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, append))) {
            if (!fileExists || !append) {
                String[] header = {"ID", "Type", "Amount", "Date", "BankAccountID", "CategoryID", "Description"};
                writer.writeNext(header);
            }

            String[] data = {
                    String.valueOf(operation.getId()),
                    operation.getType() == 0 ? "Income" : "Expenses",
                    String.valueOf(operation.getAmount()),
                    operation.getDate().toString(),
                    String.valueOf(operation.getBankAccountId()),
                    String.valueOf(operation.getCategoryId()),
                    operation.getDescription()
            };
            writer.writeNext(data);
        } catch (IOException e) {
            throw new RuntimeException("CSV Export Error " + filePath, e);
        }
    }

    @Override
    public void visit(BankAccount bankAccount, String fileName, boolean append) {
        exportBankAccountToCsv(bankAccount, basePath + fileName, append);
    }

    @Override
    public void visit(Category category, String fileName, boolean append) {
        exportCategoriesToCsv(category, basePath + fileName, append);
    }

    @Override
    public void visit(Operation operation, String fileName, boolean append) {
        exportOperationsToCsv(operation, basePath + fileName, append);
    }
}
