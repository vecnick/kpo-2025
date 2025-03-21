package bank.importer;

import bank.enums.DomainType;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import bank.report.ReportBankAccount;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@Setter
public class ImporterContext<T> {
    private ImporterStrategy<T> strategy;

    public Report<T> parse(Class<T> clazz, String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        return strategy.parse(clazz, bufferedReader);
    }
}
