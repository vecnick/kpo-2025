package hse.kpo.config;

import java.io.InputStream;
import java.io.PrintStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamConfig {

    @Bean
    public InputStream consoleInputStream() {
        return System.in;
    }
    @Bean
    public PrintStream consolePrintStream() {
        return System.out;
    }

}
