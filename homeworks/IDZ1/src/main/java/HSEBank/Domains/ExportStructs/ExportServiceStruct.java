package HSEBank.Domains.ExportStructs;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.Operations.Operation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExportServiceStruct {
    List<Operation> operations;
    List<BankAccount> accounts;
    List<Category> categories;

    public ExportServiceStruct() {}

    @JsonCreator
    public ExportServiceStruct(@JsonProperty("operationName") List<Operation> operations,
                     @JsonProperty("amount") List<BankAccount> accounts,
                     @JsonProperty("date") List<Category> categories) {
        this.operations = operations;
        this.accounts = accounts;
        this.categories = categories;
    }
}
