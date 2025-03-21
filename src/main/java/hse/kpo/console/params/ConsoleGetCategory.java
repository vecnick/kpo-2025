package hse.kpo.console.params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.enums.OperationType;
import hse.kpo.exceptions.ValueException;

@Component
public class ConsoleGetCategory {
    @Autowired
    InputReader reader;

    public String getName() {
        return reader.readString("Enter a category name: ");
    }

    public OperationType getType() throws ValueException {
        while (true) {
            var val = reader.readInt("Enter a category type -  0 for incomes, 1 for expenses: ");
            if (val == 0) {
                return OperationType.INCOME;
            } else if (val == 1) {
                return OperationType.OUTCOME;
            }
        }
    }
}