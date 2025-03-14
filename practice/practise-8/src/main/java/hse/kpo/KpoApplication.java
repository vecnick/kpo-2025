package hse.kpo;

import hse.kpo.enums.ReportFormat;
import hse.kpo.facade.Hse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Точка входа в приложение.
 */
@SpringBootApplication
public class KpoApplication {
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(KpoApplication.class, args);
		Hse hse = context.getBean(Hse.class);

		hse.addCustomer("Ivan1",6,4, 150);
		hse.addCustomer("Maksim", 4, 6, 80);
		hse.addCustomer("Petya", 6, 6, 20);
		hse.addCustomer("Nikita", 4, 4, 300);

		hse.addPedalCar(6);
		hse.addPedalCar(6);

		hse.addHandCar();
		hse.addHandCar();

		hse.sell();

		// System.out.println(hse.generateReport());

		// Экспорт в консоль в формате Markdown
		hse.exportReport(ReportFormat.MARKDOWN, new PrintWriter(System.out));

		// Экспорт в файл в формате MARKDOWN
		try (FileWriter fileWriter = new FileWriter("report.MD")) {
			hse.exportReport(ReportFormat.MARKDOWN, fileWriter);
		}

		// Экспорт в файл в формате JSON
		try (FileWriter fileWriter = new FileWriter("report.json")) {
			hse.exportReport(ReportFormat.JSON, fileWriter);
		}
    }
}
