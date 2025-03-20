package HSEBank.Interfaces;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Operations.Operation;
import java.util.List;

public interface OperationsStorageI {
    void addOperation(Operation operation);
    void updateOperation(Operation operation);
    void deleteOperation(int id);
    Operation getOperation(int id);
    List<Operation> fetchAccountOperations(int accountId);
    void export(ExporterI exporter);
}
