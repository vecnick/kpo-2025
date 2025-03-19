package bank.storages;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IOperationFactory;
import bank.interfaces.IOperationStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OperationsStorage implements IOperationStorage {
    private final List<Operation> operations = new ArrayList<>();
    private int operationsCounter = 0;

    @Override
    public void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bankAccountId, double amount, LocalDateTime date, String description, Category categoryId) {
        Operation operation = operationFactory.create(operationsCounter, type, bankAccountId, amount, date, description, categoryId);
        operations.add(operation);
        ++operationsCounter;
    }

    @Override
    public List<Operation> getOperations() {
        return operations;
    }

    @Override
    public void removeOperationById(int id) {
        operations.removeIf(op -> op.getId() == id);
    }
}
