package finance.interfaces;

import finance.domains.BankAccount;

public interface IBankAccountFactory {
    BankAccount createBankAccount(int id, String name, double balance);
}
