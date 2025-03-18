package bank.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

import java.util.List;

public interface IOperationStorage {
    void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bank_account_id, int amount, String date, String description, Category category_id);

    List<Operation> getOperations();
}
