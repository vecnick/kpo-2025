package HSEBank.Interfaces;

import HSEBank.Domains.Accounts.BankAccount;

public interface BankAccountFactoryI {
    BankAccount createBankAccount(String acoountName);
}
