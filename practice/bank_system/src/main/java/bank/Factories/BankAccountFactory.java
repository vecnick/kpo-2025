package bank.Factories;

import bank.domains.BankAccount;
import bank.interfaces.IBankAccountFactory;

public class BankAccountFactory implements IBankAccountFactory {

    @Override
    public BankAccount create(int id, String name, int balance) {
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным");
        }
        return new BankAccount(id, name, balance);
    }
}
