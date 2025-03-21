package hse.kpo.console.params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;

@Component
public class ConsoleGetBankAccount{
    @Autowired
    InputReader reader;

    public String getName() {
        return reader.readString("Enter an account name: ");
    }

    public int getBalance() {
        return reader.readInt("Enter an account balance: ");
    }
}