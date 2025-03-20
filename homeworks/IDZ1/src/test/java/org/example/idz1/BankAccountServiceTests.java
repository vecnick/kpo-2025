package org.example.idz1;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Enums.OperationTypes;
import HSEBank.Factories.BankAccountFactory;
import HSEBank.Idz1Application;
import HSEBank.Interfaces.*;
import HSEBank.Services.BankAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@SpringBootTest(classes = Idz1Application.class)
@ComponentScan(basePackages = "HSEBank")
class BankAccountServiceTests {
    private static boolean initialized = false;

    @BeforeEach
    public void init() {
        if (!initialized) {
            bankAccountService.createBankAccount("Alpha");
            bankAccountService.createBankAccount("Betha");
            bankAccountService.createBankAccount("Gamma");
            bankAccountService.performOperation(OperationTypes.EXPENSE, 0, 1000.0, LocalDate.of(2000, 1, 1), "Food", "Restaurant");
            bankAccountService.performOperation(OperationTypes.EXPENSE, 0, 2000.0, LocalDate.of(2000, 1, 2), "Food");
            bankAccountService.performOperation(OperationTypes.INCOME, 1, 100000.0, LocalDate.of(2000, 1, 1), "Salary");
            bankAccountService.performOperation(OperationTypes.INCOME, 1, 5555.1, LocalDate.of(2000, 1, 2), "Salary");
            bankAccountService.performOperation(OperationTypes.INCOME, 2, 2000.0, LocalDate.of(2000, 1, 1), "Business");
            bankAccountService.performOperation(OperationTypes.EXPENSE, 2, 2000.0, LocalDate.of(2000, 1, 2), "Presents");
            initialized = true;
        }
    }

    @Autowired BankAccountService bankAccountService;

    @Test
    @DisplayName("get details Test")
    public void getDetailsTest() {
        var res = bankAccountService.getBankAccountDetails(0);
        Assertions.assertNotNull(res);
        Assertions.assertEquals("Alpha", res.get().getAccountName());
        Assertions.assertEquals(-3000.0, res.get().getBalance());
        Assertions.assertEquals(0, res.get().getId());
    }

    @Test
    @DisplayName("recalculate Test")
    public void recalculateTest() {
        bankAccountService.performOperation(OperationTypes.EXPENSE, 0, 2000.0, LocalDate.of(2000, 1, 2), "Food");
        bankAccountService.performOperation(OperationTypes.INCOME, 0, 2000.0, LocalDate.of(2000, 1, 2), "Food", "Cafe");
        var res1 = bankAccountService.recalculateBalance(0);
        var res2 = bankAccountService.recalculateBalance(2);

        Assertions.assertEquals(-3000.0, res1);
        Assertions.assertEquals(0.0, res2);
    }

    @Test
    @DisplayName("statistics map Test")
    public void statsMapTest() {
        var stat = bankAccountService.getBankAccountAnalytics(2, LocalDate.of(1999, 12, 1), LocalDate.of(2000, 10, 1));
        var map = stat.get().getValuesByCategories();
        Assertions.assertNotNull(map.get(OperationTypes.EXPENSE));
        Assertions.assertNotNull(map.get(OperationTypes.INCOME));
        Assertions.assertEquals(1, map.get(OperationTypes.EXPENSE).size());
        Assertions.assertEquals(1, map.get(OperationTypes.INCOME).size());
        Assertions.assertEquals(2000.0, stat.get().getIncomeSum());
        Assertions.assertEquals(2000.0, stat.get().getExpensesSum());
    }

    @Test
    @DisplayName("delete Test")
    public void deleteTest() {
        bankAccountService.deleteBankAccount(0);
        bankAccountService.getBankAccountDetails(0);
    }

    @Test
    @DisplayName("import Test")
    public void importJsonTest() throws IOException {
        String content = Files.readString(Path.of("a.json"));
        bankAccountService.importServiceInfo("a.json");
        bankAccountService.exportServiceInfo("a.json");
        String content1 = Files.readString(Path.of("a.yaml"));
//        Assertions.assertEquals(content, content1);
    }

    @Test
    @DisplayName("import Test")
    public void importYamlTest() throws IOException {
        String content = Files.readString(Path.of("a.yaml"));
        bankAccountService.importServiceInfo("a.yaml");
        bankAccountService.exportServiceInfo("a.yaml");
        String content1 = Files.readString(Path.of("a.yaml"));
//        Assertions.assertEquals(content, content1);
    }



}