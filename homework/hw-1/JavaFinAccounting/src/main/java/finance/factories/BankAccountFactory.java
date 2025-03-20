package finance.factories;

import finance.domains.BankAccount;
import finance.interfaces.IBankAccountFactory;

public class BankAccountFactory implements IBankAccountFactory {
    @Override
    public BankAccount createBankAccount(int id, String name, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }

        return new BankAccount(id, name, balance);
    }
}
