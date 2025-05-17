package erp;

import erp.console.ConsoleApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConsoleApp consoleApp = new ConsoleApp();
        consoleApp.start();
    }
}
