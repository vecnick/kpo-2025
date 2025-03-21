package hse.kpo.facades;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.entities.BankAccount;
import hse.kpo.exceptions.ValueException;
import hse.kpo.factories.BankAccountFactory;
import hse.kpo.params.BankAccountParams;
import hse.kpo.storages.IdEntityStorage;
import hse.kpo.types.Id;

@Component
public class BankAccountFacade {
    
    BankAccountFactory factory;
    IdEntityStorage<BankAccount> storage;
    int max_id = 0;

    @Autowired
    public BankAccountFacade(BankAccountFactory factory, IdEntityStorage<BankAccount> storage)
    {
        this.factory = factory;
        this.storage = storage;
    }

    public List<BankAccount> getBankAccounts()
    {
        return storage.getEntities();
    }

    public void createBankAccount(BankAccountParams params) throws ValueException
    {
        storage.add(factory.createBankAccount(new Id(max_id), params));
        max_id++;
    }

    public Optional<BankAccount> getBankAccountbyId(Id id)
    {
        return storage.get(id);
    }

    public void removeBankAccount(Id id)
    {
        storage.remove(id);
    }

    public void addSumToAccount(Id id, int delta) {
        var rawAccount = storage.get(id);
        if (rawAccount.isEmpty()) {
            throw new NullPointerException("Account not found");
        }
        var account = rawAccount.get();
        account.setBalance(account.getBalance() + delta);
    }
}
