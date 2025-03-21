package hse.kpo.console.params;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.console.InputReader;
import hse.kpo.enums.OperationType;
import hse.kpo.exceptions.ValueException;
import hse.kpo.types.Id;

@Component
public class ConsoleGetOperation {
    @Autowired
    InputReader reader;

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

    public Id getBankAccountId() throws ValueException {
        return new Id(reader.readInt("Enter a target bank account id: "));
    }

    public Id getCategoryId() throws ValueException {
        return new Id(reader.readInt("Enter a target category id: "));
    }

    public int getAmount() {
        return reader.readInt("Enter an operation value: ");
    }

    public String getDescription() {
        return reader.readString("Enter an operation description: ");
    }

    public Date getDate() throws ParseException {
        return reader.readDate("Enter an operation date: ");
    }
}