package hse.kpo;

import hse.kpo.Enums.ReportFormat;
import hse.kpo.Enums.TransportReportFormat;
import hse.kpo.Facades.Hse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

/**
 * класс main.
 */
@SpringBootApplication
public class KpoApplication {

//    @Autowired
//    public SalesObserver salesObserver;
    /**
     * Функция main.
     *
     * @param args - аргументы командной строки.
     */
    public static void main(String[] args) {
        var context = SpringApplication.run(KpoApplication.class, args);

        final Hse hse = context.getBean(Hse.class);

//        hse.addCustomer("Ivan1",6,4, 1500);
//        hse.addCustomer("Maksim", 4, 6, 800);
//        hse.addCustomer("Petya", 6, 6, 250);
//        hse.addCustomer("Nikita", 4, 4, 300);

        try (BufferedReader reader = new BufferedReader(new FileReader("cars.csv"))) {
            hse.importTransportFromCSV(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter("report.csv")) {
            hse.exportTransport(TransportReportFormat.CSV, fileWriter);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter("report.xml")) {
            hse.exportTransport(TransportReportFormat.XML, fileWriter);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        //hse.sell();


        hse.exportReport(ReportFormat.MARKDOWN, new PrintWriter(System.out));

        try (FileWriter fileWriter = new FileWriter("report.MD")) {
            hse.exportReport(ReportFormat.MARKDOWN, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter("report.json")) {
            hse.exportReport(ReportFormat.JSON, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(hse.generateReport());
    }
}
