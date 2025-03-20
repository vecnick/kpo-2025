package finance;

import finance.facades.FinanceFacade;
import finance.interfaces.ICommand;
import finance.utilities.CommandExecutor;
import finance.utilities.CommandParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FinanceFacade financeFacade = new FinanceFacade();

        CommandParser parser = new CommandParser(financeFacade);
        CommandExecutor executor = new CommandExecutor();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your command: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                ICommand command = parser.parse(input);
                executor.executeCommand(command);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}