package bank.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

import java.time.LocalDateTime;
import java.util.List;

public interface IOperationStorage {
    void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bankAccountId, double amount, LocalDateTime date, String description, Category categoryId);

    void setOperations(List<Operation> operations);

    List<Operation> getOperations();

    void removeOperationById(int id);
}
