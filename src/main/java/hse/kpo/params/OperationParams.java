package hse.kpo.params;

import java.util.Date;

import hse.kpo.enums.OperationType;
import hse.kpo.types.Id;

public record OperationParams(OperationType type, Id bank_account_id, int amount, Date date, String description, Id category_id) {
    
}
