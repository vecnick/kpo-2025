package finance.factories;

import finance.domains.Operation;
import finance.interfaces.IOperationFactory;

import java.time.LocalDate;

public class OperationFactory implements IOperationFactory {
    @Override
    public Operation createOperation(int id, int type, double amount, LocalDate date,
                                     int bankAccountId, int categoryId, String description) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }

        if (type != 0 && type != 1) {
            throw new IllegalArgumentException("Operation type must be 0 or 1.");
        }

        return new Operation.OperationBuilder(id, type, amount)
                .date(date)
                .bankAccountId(bankAccountId)
                .categoryId(categoryId)
                .description(description)
                .build();
    }
}
