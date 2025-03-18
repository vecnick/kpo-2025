package bank.Factories;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IOperationFactory;

import static bank.utils.Date.isDateFormat;

public class OperationFactory implements IOperationFactory {

    @Override
    public Operation create(int id, OperationType type, BankAccount bank_account_id, int amount, String date, String description, Category category_id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount (сумма операции) не может быть отрицательным");
        }
        if (!isDateFormat(date)) {
            throw new IllegalArgumentException("Не верный формат даты");
        }
        return new Operation(id, type, bank_account_id, amount, date, description, category_id);
    }
}
