package finance.services;

import finance.domains.Operation;
import finance.factories.OperationFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OperationService {
    private int idCounter = 0;

    private HashMap<Integer, Operation> operations = new HashMap<>();

    private OperationFactory factory = new OperationFactory();

    public void createOperation(int type, double amount, LocalDate date,
                                int bankAccountId, int categoryId, String description) {
        Operation operation = factory.createOperation(idCounter, type, amount, date,
                bankAccountId, categoryId, description);

        operations.put(operation.getId(), operation);

        idCounter++;
    }

    public void updateOperation(int id, int type, double amount, LocalDate date,
                                int bankAccountId, int categoryId, String description) {
        if (!operations.containsKey(id)) {
            throw new IllegalArgumentException("There is no operations with such id.");
        }

        Operation operation = factory.createOperation(id, type, amount, date, bankAccountId, categoryId, description);

        operations.put(id, operation);
    }

    public void deleteOperation(Integer id) {
        if (!operations.containsKey(id)) {
            throw new IllegalArgumentException("There is no accounts with such id.");
        }

        operations.remove(id);
    }

    public Operation getOperation(Integer id) {
        return operations.get(id);
    }

    public List<Operation> getAllOperations() {
        return new ArrayList<>(operations.values());
    }

    public void importOperation(int id, int type, double amount, LocalDate date,
                                int bankAccountId, int categoryId, String description) {
        Operation operation = factory.createOperation(id, type, amount, date,
                bankAccountId, categoryId, description);

        operations.put(id, operation);
    }
}
