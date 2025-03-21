package hse.kpo.export.visitors.CSVvisitors;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import hse.kpo.entities.Category;
import hse.kpo.entities.Operation;
import hse.kpo.interfaces.Visitor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class CsvOperationVisitor  implements Visitor<Operation> {
    @Setter
    PrintWriter writer;

    static final String SEPARATOR = ";";

    @Override
    public void visit(Operation operation) {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (operation.getDescription().isPresent())
        {
            writer.println(String.join(
                    SEPARATOR,
                    operation.getId().toString(),
                    operation.getType().toString(),
                    operation.getBank_account_id().toString(),
                    String.valueOf(operation.getAmount()),
                    dateFormat.format(operation.getDate()),
                    operation.getCategory_id().toString(),
                    operation.getDescription().get()
            ));
        }
        else
        {
            writer.println(String.join(
                    SEPARATOR,
                    operation.getId().toString(),
                    operation.getType().toString(),
                    operation.getBank_account_id().toString(),
                    String.valueOf(operation.getAmount()),
                    dateFormat.format(operation.getDate()),
                    operation.getCategory_id().toString()
            ));
        }
    }
}
