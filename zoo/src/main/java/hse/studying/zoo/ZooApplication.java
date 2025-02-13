package hse.studying.zoo;

import hse.studying.zoo.services.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ZooApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZooApplication.class, args);
    }
}

@Component
class ConsoleRunner implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;

    public ConsoleRunner(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    @Override
    public void run(String... args) {
        consoleMenu.start(); // Запускаем консольное меню после старта Spring Boot
    }
}