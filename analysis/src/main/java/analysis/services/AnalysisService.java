package analysis.services;

import analysis.domains.StatisticEntity;
import analysis.repositories.StatisticRepository;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AnalysisService {
    private RestTemplate restTemplate;

    private FileService fileService;

    private StatisticRepository statisticRepository;

    public Boolean checkPlagiarise(Integer fileId) {
        return fileService.getSameCount(fileId) > 1;
    }

    public byte[] generateWordCloud(Integer fileId) {
        String content = fileService.getFileContent(fileId);

        String encodedText = URLEncoder.encode(content, StandardCharsets.UTF_8);
        String url = String.format(
                "https://quickchart.io/",
                encodedText
        );

        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return response.getBody();
    }

    public StatisticEntity analyze(Integer fileId) {
        var statisticOptional = statisticRepository.getByFileId(fileId);
        if (statisticOptional.isPresent()) {
            return statisticOptional.get();
        }

        String text = fileService.getFileContent(fileId);

        int paragraphs = countParagraphs(text);
        int words = countWords(text);
        int symbols = text.length();

        var result = new StatisticEntity();
        result.setFileId(fileId);
        result.setParagraphs(paragraphs);
        result.setWords(words);
        result.setSymbols(symbols);

        result = statisticRepository.save(result);
        return result;
    }

    private int countParagraphs(String text) {
        var pars = Arrays.asList(text.trim().split("\\n\\s*\\n"));
        pars.removeIf(String::isBlank);
        return pars.size();
    }

    private int countWords(String text) {
        return text.trim().split("\\s+").length;
    }
}