package bank.storages;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.IOperationFactory;
import bank.interfaces.IOperationStorage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OperationStorage implements IOperationStorage {
    private List<Operation> operations = new ArrayList<>();
    private int operationsCounter = 0;

    @Override
    public void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bankAccountId, double amount, LocalDateTime date, String description, Category categoryId) {
        Operation operation = operationFactory.create(operationsCounter, type, bankAccountId, amount, date, description, categoryId);
        operations.add(operation);
        ++operationsCounter;
    }

    @Override
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
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
