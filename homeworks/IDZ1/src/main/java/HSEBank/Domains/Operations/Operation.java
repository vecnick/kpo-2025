package HSEBank.Domains.Operations;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Enums.OperationTypes;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Operation {
    private int id;
    private OperationTypes type;
    private int bankAccountId;
    private double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String description;
    private int categoryId;

//    public Operation(int id, OperationTypes type, int bankAccountId, double amount, LocalDate date, String description, int categoryId) {
//        this.id = id;
//        this.type = type;
//        this.bankAccountId = bankAccountId;
//        this.amount = amount;
//        this.date = date;
//        this.description = description;
//        this.categoryId = categoryId;
//    }

    @JsonCreator
    public Operation(@JsonProperty("id") int id, @JsonProperty("type") OperationTypes type,
                     @JsonProperty("bankAccountId") int bankAccountId, @JsonProperty("amount") double amount,
                     @JsonProperty("date") LocalDate date, @JsonProperty("description") String description,
                     @JsonProperty("categoryId") int categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }
}
