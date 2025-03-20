package HSEBank.Importers.ServiceImport;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.Operations.Operation;
import HSEBank.Services.BankAccountService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AbstractServiceImporter {
    private final BankAccountService bankAccountService;
    ArrayList<BankAccount> bankAccounts = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<Operation> operations = new ArrayList<>();
    final String fileName;
    BufferedReader reader;

    public AbstractServiceImporter(BankAccountService bankAccountService, String fileName) {
        this.fileName = fileName;
        this.bankAccountService = bankAccountService;
    }

    public AbstractServiceImporter(WeakReference<BankAccountService> bankAccountService, String fileName) {
        this.fileName = fileName;
        this.bankAccountService =bankAccountService.get();
    }

    public void importService() {
        openFile();

        try {
            parseFile();
        } catch (IOException e) {
            System.out.println("Error importing service" + e.getMessage());
        }
        importToService();

        closeFile();
    }

    public void openFile() {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void parseFile() throws IOException {}
    public void importToService() {
        var service = bankAccountService;

        for (BankAccount bankAccount : bankAccounts) {
            try {
                service.createBankAccount(bankAccount.getAccountName());
            } catch (Exception e) {
                System.out.println("Error importing service" + e.getMessage());
            }
        }

        for (Operation operation : operations) {
            try {
                var cat = categories.stream().filter(p -> p.getId() == operation.getCategoryId()).findFirst().orElse(null);
                if (cat != null) {
                    service.performOperation(operation.getType(), operation.getBankAccountId(),
                            operation.getAmount(), operation.getDate(), cat.getName(), operation.getDescription());
                }

            } catch (Exception e) {
                System.out.println("Error importing service" + e.getMessage());
            }
        }
    }
    public void closeFile() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
