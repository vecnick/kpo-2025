package HSEBank.Interfaces;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.Operations.Operation;

import java.io.IOException;
import java.util.List;

public interface ExporterI {
    void exportOperations(List<Operation> operations) throws IOException;
    void exportAccounts(List<BankAccount> accounts) throws IOException;
    void exportCategories(List<Category> categories) throws IOException;
    void export();
}
