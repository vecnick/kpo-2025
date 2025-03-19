package bank.services;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.interfaces.ICategoryFactory;
import bank.interfaces.IOperationFactory;
import bank.interfaces.IOperationStorage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static bank.utils.Date.stringToDateTime;

public class OperationService {
    private final IOperationStorage operationStorage;

    public OperationService(IOperationStorage operationStorage) {
        this.operationStorage = operationStorage;
    }

    public void addOperation(IOperationFactory operationFactory, OperationType type, BankAccount bankAccountId, int amount, String description, Category categoryId) {
        LocalDateTime date = LocalDateTime.now();
        operationStorage.addOperation(operationFactory, type, bankAccountId, amount, date, description, categoryId);
    }

    public List<Operation> getOperations() {
        return operationStorage.getOperations();
    }

    public Optional<Operation> getOperationById(int id) {
        return getOperations().stream()
                .filter(op -> op.getId() == id) // фильтруем
                .findFirst(); // берём первый попавшийся
    }

    public void removeOperationById(int id) {
        operationStorage.removeOperationById(id);
    }

    public Map<String, List<Operation>> groupOperationsByCategoryName() {
        return getOperations().stream().collect(Collectors.groupingBy(op -> op.getCategoryId().getName()));
    }

    public double getAmountDifferenceByPeriod(String dateFrom, String dateTo) {
        List<Operation> incomeOperations = getOperationsIncome(getOperations());
        List<Operation> outcomeOperations = getOperationsOutcome(getOperations());

        LocalDateTime from = stringToDateTime(dateFrom);
        LocalDateTime to = stringToDateTime(dateTo);

        double income = incomeOperations.stream().filter(op -> {
                    LocalDateTime operationDate = op.getDate(); // получаем дату операции
                    return (operationDate.isAfter(from) || operationDate.isEqual(from)) &&
                            (operationDate.isBefore(to) || operationDate.isEqual(to));}) // фильтруем по диапазону
                    .mapToDouble(Operation::getAmount).sum(); // Суммируем

        double outcome = outcomeOperations.stream().filter(op -> {
                    LocalDateTime operationDate = op.getDate(); // получаем дату операции
                    return (operationDate.isAfter(from) || operationDate.isEqual(from)) &&
                            (operationDate.isBefore(to) || operationDate.isEqual(to));}) // фильтруем по диапазону
                    .mapToDouble(Operation::getAmount).sum(); // Суммируем

        return income - outcome;
    }

    static public List<Operation> getOperationsIncome(List<Operation> operations) {
        return operations.stream()
                .filter(op -> op.getType() == OperationType.INCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }

    static public List<Operation> getOperationsOutcome(List<Operation> operations) {
        return operations.stream()
                .filter(op -> op.getType() == OperationType.OUTCOME) // проверка типа
                .collect(Collectors.toList()); // составление нового List
    }

    static public List<Operation> getOperationsByAccount(List<Operation> operations, BankAccount account) {
        return operations.stream()
                .filter(op -> Objects.deepEquals(op.getBankAccountId(), account)) // рекурсвиное сравнение полей объектов
                .collect(Collectors.toList()); // составление нового List
    }
}
