package hse.kpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Точка входа в приложение.
 */
@SpringBootApplication
@EnableScheduling
public class KpoApplication {
	public static void main(String[] args) {
		SpringApplication.run(KpoApplication.class, args);
	}
}
