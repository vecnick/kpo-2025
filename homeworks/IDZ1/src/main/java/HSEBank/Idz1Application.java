package HSEBank;

import HSEBank.Parser.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class Idz1Application {
    private final Parser commandParser;

    private void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HSE Bank application");
        System.out.println("Enter any of available commands");

        while (true) {
            System.out.print(">> ");
            var input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            commandParser.parseCommandLine(input.split(" "));
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Idz1Application.class, args);
        Idz1Application application = context.getBean(Idz1Application.class);
        application.run();
    }

}
