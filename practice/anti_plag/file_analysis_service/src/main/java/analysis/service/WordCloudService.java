package analysis.service;

import analysis.interfaces.IFileInfoServiceMediator;
import analysis.interfaces.IWordCloudService;
import analysis.record.WordCloudPicParams;
import analysis.record.WordCloudRequestParams;
import analysis.util.WordCloudPicDateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class WordCloudService implements IWordCloudService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Path filesDir;
    private final IFileInfoServiceMediator fileInfoServiceMediator;

    public WordCloudService(Path filesDir, IFileInfoServiceMediator fileInfoServiceMediator) {
        this.filesDir = filesDir;
        this.fileInfoServiceMediator = fileInfoServiceMediator;
    }

    @Override
    public WordCloudPicParams getWordCloud(String text) throws IOException, InterruptedException {

        WordCloudRequestParams wordCloudRequestParams = WordCloudRequestParams.builder()
                .format("png")
                .width(1000)
                .height(1000)
                .fontFamily("sans-serif")
                .fontScale(15)
                .scale("linear")
                .backgroundColor("white")
                .text(text)
                .build();

        String jsonParams = new ObjectMapper().writeValueAsString(wordCloudRequestParams);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://quickchart.io/wordcloud"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonParams))
                .setHeader("Content-Type", "application/json")
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        String filename = "wordcloud"
                + "_"
                + WordCloudPicDateUtil.getLocalDateTimeStr()
                + ".png";

        // Записываем полученные байты в файл
        byte[] imageBytes = response.body();
        Path path = filesDir.resolve(filename);
        Files.write(path, imageBytes);

        return new WordCloudPicParams(path.toString(), filename);
    }
}
