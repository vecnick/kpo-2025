package HSEBank.Interfaces;

import HSEBank.Domains.Operations.Operation;
import HSEBank.Enums.OperationTypes;

import java.time.LocalDate;
import java.util.Date;

public interface OperationFactoryI {
    public Operation createOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, int categoryId,
                                     String description);

    public Operation createOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, int categoryId);
}
