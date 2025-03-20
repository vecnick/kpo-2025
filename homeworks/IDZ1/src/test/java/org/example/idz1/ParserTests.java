package org.example.idz1;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Enums.OperationTypes;
import HSEBank.Factories.BankAccountFactory;
import HSEBank.Idz1Application;
import HSEBank.Interfaces.*;
import HSEBank.Parser.Parser;
import HSEBank.Services.BankAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@SpringBootTest(classes = Idz1Application.class)
class ParserTests {
    private static boolean initialized = false;

    Parser parser = new Parser(new BankAccountServiceMock());


    @Test
    @DisplayName("try create, delete, get-details operations Test")
    public void getDetailsTest() {
        parser.parseCommandLine(new String[]{"create-account", "Main"});
        parser.parseCommandLine(new String[]{"delete-account", "0"});
        parser.parseCommandLine(new String[]{"get-details", "0"});
    }

    @Test
    @DisplayName("try import export with mock Test")
    public void importExportCommandsTest() {
        parser.parseCommandLine(new String[]{"import", "test.json"});
        parser.parseCommandLine(new String[]{"export", "testResult.json"});
    }

    @Test
    @DisplayName("try perform operation with mock Test")
    public void performOperationCommandsTest() {
        parser.parseCommandLine(new String[]{"get-analytics", "0", "2006-01-01", "2008-01-01"});
        parser.parseCommandLine(new String[]{"recalculate-balance", "0"});
        parser.parseCommandLine(new String[]{"perform-operation", "income", "0", "30000", "2007-12-03", "Job", "salary"});
    }
}