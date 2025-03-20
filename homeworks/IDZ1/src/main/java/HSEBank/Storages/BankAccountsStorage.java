package HSEBank.Storages;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Operations.Operation;
import HSEBank.Interfaces.BankAccountStorageI;
import HSEBank.Interfaces.ExporterI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class BankAccountsStorage implements BankAccountStorageI {
    List<BankAccount> accounts = new ArrayList<>();

    @Override
    public void addBankAccount(BankAccount account) throws IllegalArgumentException {
        if (!accounts.contains(account)) {
            accounts.add(account);
        } else {
            throw new IllegalArgumentException("Account already exists");
        }
    }

    @Override
    public void updateBankAccount(BankAccount account) throws IllegalArgumentException {
        if (accounts.contains(account)) {
            accounts.set(accounts.indexOf(account), account);
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }

    }

    @Override
    public void deleteBankAccount(int id) throws IllegalArgumentException {
        boolean wasDeleted = false;

        for (BankAccount account: accounts) {
            if (account.getId() == id) {
                accounts.remove(account);
                wasDeleted = true;
                break;
            }
        }

        if (!wasDeleted) {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    @Override
    public BankAccount getBankAccount(int id) throws NoSuchElementException {
        return accounts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void export(ExporterI exporter) {
        try {
            exporter.exportAccounts(accounts);
        } catch (Exception e) {
            System.out.println("Error exporting operations " + e.getMessage());
        }
    }
}
