package HSEBank.Interfaces;

import HSEBank.Domains.Accounts.BankAccount;

public interface BankAccountStorageI {
    void addBankAccount(BankAccount bankAccount);
    void updateBankAccount(BankAccount bankAccount);
    void deleteBankAccount(int id);
    BankAccount getBankAccount(int id);
    void export(ExporterI exporter);
}
