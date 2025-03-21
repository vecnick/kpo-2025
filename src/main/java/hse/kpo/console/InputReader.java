package hse.kpo.console;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputReader {

    private Scanner scanner;

    PrintStream out;

    @Autowired
    public InputReader(InputStream source, PrintStream out) {
        this.scanner = new Scanner(source);
        this.out = out;
    }

    /**
     * Метод для считывания целого числа.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Введенное пользователем число.
     */
    public int readInt(String prompt) {
        do {
            out.print(prompt);
            while (!scanner.hasNextLine());
            String line = scanner.nextLine();
            try {
                int number = Integer.parseInt(line);
                return number;
            } catch (NumberFormatException exception) {
                out.println("Invalid number, try again.\n");
            }
        } while (true);
    }

    /**
     * Метод для считывания строки.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Введенная пользователем строка.
     */
    public String readString(String prompt) {
        out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Метод для считывания даты.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @param format Формат даты (например, "dd.MM.yyyy").
     * @return Введенная пользователем дата в виде объекта LocalDate.
     * @throws ParseException 
     */
    public Date readDate(String prompt) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        while (true) {
            try {
                out.print(prompt);
                String dateString = scanner.nextLine();
                return formatter.parse(dateString);
            } catch (Exception e) {
                out.println("invalid date format.\n");
            }
        }
    }

    /**
     * Закрывает Scanner.
     */
    public void close() {
        scanner.close();
    }
}
