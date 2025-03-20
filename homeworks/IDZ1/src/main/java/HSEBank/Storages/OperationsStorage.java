package HSEBank.Storages;

import HSEBank.Domains.Operations.Operation;
import HSEBank.Interfaces.ExporterI;
import HSEBank.Interfaces.OperationsStorageI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperationsStorage implements OperationsStorageI {
    List<Operation> operations = new ArrayList<>();

    @Override
    public void addOperation(Operation operation) throws IllegalArgumentException {
        if (!operations.contains(operation)) {
            operations.add(operation);
        } else {
            throw new IllegalArgumentException("Operation already exists");
        }
    }

    @Override
    public void updateOperation(Operation operation) throws IllegalArgumentException {
        if (operations.contains(operation)) {
            operations.set(operations.indexOf(operation), operation);
        } else {
            throw new IllegalArgumentException("Operation does not exist");
        }

    }

    @Override
    public void deleteOperation(int id) {
        for (Operation operation : operations) {
            if (operation.getId() == id) {
                operations.remove(operation);
                break;
            }
        }
    }

    @Override
    public Operation getOperation(int id) {
        return operations.stream().filter(p -> p.getId() == id).toList().getFirst();
    }

    @Override
    public List<Operation> fetchAccountOperations(int accountId) {
        return operations.stream().filter(p -> p.getBankAccountId() == accountId).toList();
    }

    @Override
    public void export(ExporterI exporter) {
        try {
            exporter.exportOperations(operations);
        } catch (Exception e) {
            System.out.println("Error exporting operations " + e.getMessage());
        }
    }
}
