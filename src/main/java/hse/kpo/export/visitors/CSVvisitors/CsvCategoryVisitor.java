package hse.kpo.export.visitors.CSVvisitors;

import java.io.PrintWriter;

import org.springframework.stereotype.Component;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Category;
import hse.kpo.interfaces.Visitor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class CsvCategoryVisitor  implements Visitor<Category> {
    @Setter
    PrintWriter writer;

    static final String SEPARATOR = ";";

    @Override
    public void visit(Category category) {
        writer.println(String.join(
                SEPARATOR,
                category.getId().toString(),
                category.getType().toString(),
                category.getName()
        ));
    }
}
