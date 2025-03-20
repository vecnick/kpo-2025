package HSEBank.Factories;

import HSEBank.Domains.Operations.Operation;
import HSEBank.Enums.OperationTypes;
import HSEBank.Interfaces.OperationFactoryI;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OperationFactory implements OperationFactoryI {
    private int curId = 0;

    @Override
    public Operation createOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, int categoryId, String description) {
        return new Operation(curId++, type, bankAccountId, amount, date, description, categoryId);
    }

    @Override
    public Operation createOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, int categoryId) {
        return new Operation(curId++, type, bankAccountId, amount, date, "", categoryId);
    }
}
