package hse.kpo;

import hse.kpo.config.S3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Точка входа в приложение.
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(S3Config.class)
public class KpoApplication {
	public static void main(String[] args) {
		SpringApplication.run(KpoApplication.class, args);
	}
}
