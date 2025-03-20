package HSEBank.Domains.Accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount {
    private int id;
    private String accountName;
    private double balance = 0.0;

    public BankAccount(int id, String accountName) {
        this.id = id;
        this.accountName = accountName;
    }

    @JsonCreator
    public BankAccount(@JsonProperty("id") int id, @JsonProperty("accountName") String accountName,
                       @JsonProperty("balance") double balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }
}
