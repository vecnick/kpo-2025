package hse.kpo.export.visitors.CSVvisitors;

import java.io.PrintWriter;

import org.springframework.stereotype.Component;

import hse.kpo.entities.BankAccount;
import hse.kpo.interfaces.Visitor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class CsvBankAccountVisitor  implements Visitor<BankAccount> {
    @Setter
    PrintWriter writer;

    static final String SEPARATOR = ";";

    @Override
    public void visit(BankAccount account) {
        writer.println(String.join(
                SEPARATOR,
                account.getId().toString(),
                account.getName(),
                String.valueOf(account.getBalance())
        ));
    }
}
