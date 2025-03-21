package hse.kpo.factories;
import org.springframework.stereotype.Component;

import hse.kpo.entities.BankAccount;
import hse.kpo.params.BankAccountParams;
import hse.kpo.types.Id;

@Component
public class BankAccountFactory {

    public BankAccount createBankAccount(Id id, BankAccountParams params)
    {
        return new BankAccount(id, params.name(), params.balance());
    }

}
