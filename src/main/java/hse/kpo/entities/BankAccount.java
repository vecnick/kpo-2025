package hse.kpo.entities;

import hse.kpo.interfaces.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import hse.kpo.types.Id;

@Getter
@ToString
public class BankAccount implements IdEntity {
    Id id;
    String name;
    @Setter
    int balance;

    public BankAccount(Id id, String name, int balance)
    {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

}
