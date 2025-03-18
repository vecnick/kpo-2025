package bank.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

public interface IOperationFactory {
    Operation create(int id, OperationType type, BankAccount bank_account_id, int amount, String date, String description, Category category_id);
}
