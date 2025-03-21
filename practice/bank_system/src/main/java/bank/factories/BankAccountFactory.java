package bank.factories;

import bank.domains.BankAccount;
import bank.interfaces.IBankAccountFactory;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFactory implements IBankAccountFactory {

    @Override
    public BankAccount create(int id, String name, double balance) {
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным");
        }
        return new BankAccount(id, name, balance);
    }
}
