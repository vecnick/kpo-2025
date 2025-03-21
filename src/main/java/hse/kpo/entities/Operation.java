package hse.kpo.entities;


import java.util.Date;
import java.util.Optional;

import hse.kpo.enums.OperationType;
import hse.kpo.interfaces.IdEntity;
import hse.kpo.types.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Operation implements IdEntity {
    Id id;
    OperationType type;
    Id bank_account_id;
    int amount;
    Date date;
    Optional<String> description;
    Id category_id;

    public Operation(Id id, OperationType type, Id bank_account_id, int amount, Date date, Id category_id, String description)
    {
        this.id = id;
        this.type = type;
        this.bank_account_id = bank_account_id;
        this.amount = amount;
        this.date = date;
        this.category_id = category_id;
        this.description = Optional.ofNullable(description);
    }
}
