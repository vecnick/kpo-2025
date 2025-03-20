package finance.interfaces;

import finance.domains.Operation;

import java.time.LocalDate;

public interface IOperationFactory {
    Operation createOperation(int id, int type, double amount, LocalDate date,
                              int bankAccountId, int categoryId, String description);
}
