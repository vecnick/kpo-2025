package storing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class FileStorageConfig {

    @Value("${upload.dir}")
    private String uploadDir;

    @Bean
    public Path filesDir() {
        String projectRoot = System.getProperty("user.dir"); // корень проекта (где находится build.gradle)
        return Path.of(projectRoot, uploadDir);
    }
}

