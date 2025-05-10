package hse.kpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TrainServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainServiceApplication.class, args);
    }
}