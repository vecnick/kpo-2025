package bank.services;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.interfaces.IBankAccountFactory;
import bank.interfaces.IBankAccountStorage;
import bank.storages.BankAccountsStorage;

import java.util.List;
import java.util.Optional;

public class BankAccountService {
    private final IBankAccountStorage bankAccountsStorage;

    public BankAccountService(IBankAccountStorage bankAccountsStorage) {
        this.bankAccountsStorage = bankAccountsStorage;
    }

    public void addAccount(IBankAccountFactory bankAccountFactory, String name, int balance) {
        bankAccountsStorage.addAccount(bankAccountFactory, name, balance);
    }

    public List<BankAccount> getAccounts() {
        return bankAccountsStorage.getAccounts();
    }

    public Optional<BankAccount> getOperationById(int id) {
        return getAccounts().stream()
                .filter(ac -> ac.getId() == id) // фильтруем
                .findFirst(); // берём первый попавшийся
    }

    public void removeAccountById(int id) {
        bankAccountsStorage.removeAccountById(id);
    }
}
