package HSEBank.Factories;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Interfaces.BankAccountFactoryI;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFactory implements BankAccountFactoryI {
    private int curId = 0;

    @Override
    public BankAccount createBankAccount(String accountName) {
        return new BankAccount(curId++, accountName);
    }
}
