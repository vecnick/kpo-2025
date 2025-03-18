package bank.storages;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IOperationFactory;
import bank.interfaces.IOperationStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OperationsStorage implements IOperationStorage {
    private final List<Operation> operations = new ArrayList<>();
    private int operationsCounter = 0;

    @Override
    public void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bank_account_id, int amount, String date, String description, Category category_id) {
        Operation operation = operationFactory.create(operationsCounter, type, bank_account_id, amount, date, description, category_id);
        operations.add(operation);
        ++operationsCounter;
    }

    @Override
    public List<Operation> getOperations() {
        return operations;
    }
}
