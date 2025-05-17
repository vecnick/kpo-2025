package bank.factories;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IOperationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OperationFactory implements IOperationFactory {

    @Override
    public Operation create(int id, OperationType type, BankAccount bankAccountId, double amount, LocalDateTime date, String description, Category categoryId) {
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount (сумма операции) не может быть отрицательным");
        }

        return new Operation(id, type, bankAccountId, amount, date, description, categoryId);
    }
}
