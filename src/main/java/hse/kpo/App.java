package hse.kpo;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import hse.kpo.console.application.ConsoleApplication;
import hse.kpo.exceptions.ValueException;

import java.util.Scanner;
// import exceptions.ValueException;

@SpringBootApplication
// public class App implements CommandLineRunner {

public class App {
    // private static Logger LOG = LoggerFactory
    //   .getLogger(App.class);

    public static void main(String[] args) throws BeansException, ParseException, ValueException {
        var context = SpringApplication.run(App.class, args);
        context.getBean(ConsoleApplication.class).run();
    }

    // @Override
    // public void run(String... args) {
    //     // LOG.info("EXECUTING : command line runner");
 
    //     // for (int i = 0; i < args.length; ++i) {
    //     //     LOG.info("args[{}]: {}", i, args[i]);
    //     // }
    //     System.out.println("hii");
    //     var scanner = new Scanner(System.in);
    //     var prompt = "enter num";
    //     do {
    //         System.out.print(prompt + ": ");
    //         String line = scanner.nextLine();
    //         try {
    //             int number = Integer.parseInt(line);
    //             System.out.println(number);
    //         } catch (NumberFormatException exception) {
    //             System.out.println("Invalid number, try again.");
    //         }
    //     } while (true);
    // }

}
