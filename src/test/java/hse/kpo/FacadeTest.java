package hse.kpo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hse.kpo.config.IdEntityStorageConfig;
import hse.kpo.enums.OperationType;
import hse.kpo.exceptions.ValueException;
import hse.kpo.facades.BankFacade;
import hse.kpo.params.BankAccountParams;
import hse.kpo.params.CategoryParams;
import hse.kpo.params.OperationParams;
import hse.kpo.types.Id;

@SpringBootTest
@Import(IdEntityStorageConfig.class)
public class FacadeTest {
    @Autowired
    BankFacade facade;

    @Test
    @DisplayName("BankFacadeTest")
    void facadeTest() throws ValueException, ParseException {
        facade.createBankAccount(new BankAccountParams("victor", 0));
        facade.createBankAccount(new BankAccountParams("pavlov", 0));
        // facade.addSumToAccount(new Id(0), 100);
        var accs = facade.getBankAccounts();
        assertEquals(accs.size(), 2);
        facade.createCategory(new CategoryParams(OperationType.INCOME, "Wage"));
        facade.createCategory(new CategoryParams(OperationType.OUTCOME, "Spendings"));
        var cats = facade.getBankAccounts();
        assertEquals(cats.size(), 2);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        facade.createOperation(new OperationParams(OperationType.INCOME, new Id(0), 10, formatter.parse("11.10.2006"),
                "hii", new Id(0)));
        facade.createOperation(new OperationParams(OperationType.OUTCOME, new Id(1), 10, formatter.parse("11.10.2006"),
                "hii", new Id(1)));
        facade.createOperation(new OperationParams(OperationType.INCOME, new Id(1), 10, formatter.parse("11.10.2006"),
                "hii", new Id(0)));
        facade.removeBankAccount(new Id(0));
        facade.removeCategory(new Id(1));
    }

}
