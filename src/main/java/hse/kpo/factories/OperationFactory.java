package hse.kpo.factories;

import org.springframework.stereotype.Component;

import hse.kpo.entities.Operation;
import hse.kpo.exceptions.ValueException;
import hse.kpo.params.OperationParams;
import hse.kpo.types.Id;


@Component
public class OperationFactory {

    public Operation createOperation(Id id, OperationParams params) throws ValueException {
        if (params.amount() < 0) {
            throw new ValueException("Operation amount must not be negative");
        }
        return new Operation(id, params.type(), params.bank_account_id(), params.amount(),
                params.date(), params.category_id(), params.description());
    }

}
