package bank.services;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.ICategoryFactory;
import bank.interfaces.IOperationFactory;
import bank.interfaces.IOperationStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bank.utils.Date.getLocalDateTime;

public class OperationService {
    private final IOperationStorage operationStorage;

    public OperationService(IOperationStorage operationStorage) {
        this.operationStorage = operationStorage;
    }

    public void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bank_account_id, int amount, String description, Category category_id) {
        String date = getLocalDateTime();
        operationStorage.addOperation(operationFactory, type, bank_account_id, amount, date, description, category_id);
    }

    public List<Operation> getOperations() {
        return operationStorage.getOperations();
    }

    public Optional<Operation> getOperationById(int id) {
        return getOperations().stream()
                .filter(op -> op.getId() == id) // фильтруем
                .findFirst(); // берём первый попавшийся
    }

    public List<Operation> getOperationsIncome() {
        return getOperations().stream()
                .filter(op -> op.getType() == OperationType.INCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }

    public List<Operation> getOperationsOutcome() {
        return getOperations().stream()
                .filter(op -> op.getType() == OperationType.OUTCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }
}
