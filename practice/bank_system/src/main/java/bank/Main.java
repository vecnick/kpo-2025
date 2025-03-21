package bank;

import bank.enums.ReportFormat;
import bank.facade.BankAccountFacade;
import bank.facade.CategoryFacade;
import bank.facade.OperationFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ConsoleApp consoleApp = context.getBean(ConsoleApp.class);
        consoleApp.start();
    }
}