package bank.storages;

import bank.domains.BankAccount;
import bank.interfaces.IBankAccountFactory;
import bank.interfaces.IBankAccountStorage;

import java.util.ArrayList;
import java.util.List;

public class BankAccountsStorage implements IBankAccountStorage {
    private final List<BankAccount> bankAccounts = new ArrayList<>();
    private int bankAccountsCounter = 0;

    @Override
    public void addAccount(IBankAccountFactory bankAccountFactory, String name, int balance) {
        BankAccount bankAccount = bankAccountFactory.create(bankAccountsCounter, name, balance);
        bankAccounts.add(bankAccount);
        ++bankAccountsCounter;
    }

    @Override
    public List<BankAccount> getAccounts() {
        return bankAccounts;
    }

    @Override
    public void removeAccountById(int id) {
        bankAccounts.removeIf(ac -> ac.getId() == id);
    }
}
