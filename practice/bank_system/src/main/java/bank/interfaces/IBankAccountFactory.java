package bank.interfaces;

import bank.domains.BankAccount;

public interface IBankAccountFactory {
    BankAccount create(int id, String name, int balance);
}
