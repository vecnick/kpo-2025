package finance.services;

import finance.domains.BankAccount;
import finance.factories.BankAccountFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankAccountService {
    private int idCounter = 0;

    @Getter
    private HashMap<Integer, BankAccount> accounts = new HashMap<>();

    private BankAccountFactory factory = new BankAccountFactory();

    public void createBankAccount(String name, double balance) {
        BankAccount account = factory.createBankAccount(idCounter, name, balance);

        accounts.put(account.getId(), account);

        idCounter++;
    }

    public void updateBankAccount(int id, String name, double balance) {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("There is no accounts with such id.");
        }

        BankAccount newAccount = factory.createBankAccount(id, name, balance);

        accounts.put(id, newAccount);
    }

    public void deleteBankAccount(int id) {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("There is no accounts with such id.");
        }

        accounts.remove(id);
    }

    public BankAccount getBankAccount(int id) {
        return accounts.get(id);
    }

    public List<BankAccount> getAllBankAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void importBankAccount(int id, String name, double balance) {
        BankAccount account = factory.createBankAccount(id, name, balance);

        accounts.put(id, account);
    }
}
