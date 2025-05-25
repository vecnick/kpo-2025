package analysis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class WordCloudStorageConfig {

    @Value("${wordcloud.dir}")
    private String wordCloudDir;

    @Bean
    public Path filesDir() {
        String projectRoot = System.getProperty("user.dir"); // корень проекта (где находится build.gradle)
        return Path.of(projectRoot, wordCloudDir);
    }
}