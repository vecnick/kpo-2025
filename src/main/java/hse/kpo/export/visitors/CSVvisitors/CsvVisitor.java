package hse.kpo.export.visitors.CSVvisitors;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Category;
import hse.kpo.entities.Operation;
import hse.kpo.export.visitors.JSONvisitors.JsonEntityVisitor;
import hse.kpo.interfaces.Visitor;
import hse.kpo.storages.IdEntityStorage;
import lombok.NoArgsConstructor;

@Component
public class CsvVisitor {

    @Autowired
    IdEntityStorage<BankAccount> accountStorage;
    @Autowired
    IdEntityStorage<Category> categoryStorage;
    @Autowired
    IdEntityStorage<Operation> operationStorage;

    CsvBankAccountVisitor BankAccountvisitor;
    CsvCategoryVisitor Categoryvisitor;
    CsvOperationVisitor Operationvisitor;

    static final String SEPARATOR = ";";

    CsvVisitor() {
        BankAccountvisitor = new CsvBankAccountVisitor();
        Categoryvisitor = new CsvCategoryVisitor();
        Operationvisitor = new CsvOperationVisitor();
    }

    public boolean writeCsv(PrintWriter printer) {
        BankAccountvisitor.setWriter(printer);
        Categoryvisitor.setWriter(printer);
        Operationvisitor.setWriter(printer);
        boolean result = true;
        accountStorage.accept(BankAccountvisitor);
        categoryStorage.accept(Categoryvisitor);
        operationStorage.accept(Operationvisitor);
        return result;
    }
}
