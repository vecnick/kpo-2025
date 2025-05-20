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
import java.nio.file.Paths;
import java.util.Optional;

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
    public Optional<WordCloudPicParams> createWordCloud(int fileId) {

        Optional<String> optText = fileInfoServiceMediator.getFileTextById(fileId);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

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

        // Получем json с заголовками параметров api
        String jsonParams = "";
        try {
            jsonParams = new ObjectMapper().writeValueAsString(wordCloudRequestParams);
        } catch (Exception e) {
            System.out.println("Не удалось сконвертировать параметры в json.");
            return Optional.empty();
        }

        // Формируем post запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://quickchart.io/wordcloud"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonParams))
                .setHeader("Content-Type", "application/json")
                .build();

        // Отправляем запрос
        HttpResponse<byte[]> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception e) {
            System.out.println("Не удалось отправить запрос WordCloud.");
            return Optional.empty();
        }

        String filename = "wordcloud"
                + "_"
                + WordCloudPicDateUtil.getLocalDateTimeStr()
                + ".png";

        // Записываем полученные байты в файл
        byte[] imageBytes = response.body();
        Path path = filesDir.resolve(filename);
        try {
            Files.write(path, imageBytes);
        } catch (Exception e) {
            System.out.println("Не удалось записать в файл картинку WordCloud.");
            return Optional.empty();
        }

        return Optional.of(new WordCloudPicParams(path.toString(), filename));
    }

    @Override
    public boolean deleteWordCloud(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Не удалось удалить картинку WordCloud - WordCloudService");
            return false;
        }
    }
}
