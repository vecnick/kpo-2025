package bank.interfaces;

import bank.domains.BankAccount;

import java.util.List;

public interface IBankAccountStorage {
    void addAccount(IBankAccountFactory bankAccountFactory, String name, int balance);

    void setAccounts(List<BankAccount> bankAccounts);

    List<BankAccount> getAccounts();

    void removeAccountById(int id);
}
