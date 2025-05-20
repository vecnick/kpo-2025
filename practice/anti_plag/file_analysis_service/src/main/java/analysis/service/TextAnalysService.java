package analysis.service;

import analysis.entity.TextAnalys;
import analysis.interfaces.IStoringGrpcImpl;
import analysis.interfaces.ITextAnalysService;
import analysis.repository.TextAnalysRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;

@Service
public class TextAnalysService implements ITextAnalysService {

    private final IStoringGrpcImpl storingGrpc;
    private final TextAnalysRepository textAnalysRepository;

    public TextAnalysService(IStoringGrpcImpl storingGrpc, TextAnalysRepository textAnalysRepository) {
        this.storingGrpc = storingGrpc;
        this.textAnalysRepository = textAnalysRepository;
    }

    @Override
    public List<TextAnalys> getAll() {
        return  textAnalysRepository.findAll();
    }

    @Override
    public Optional<TextAnalys> getByFileId(int fileId) {
        return textAnalysRepository.getByFileId(fileId);
    }

    @Override
    public Optional<String> getPathByFileId(int fileId) {
        return textAnalysRepository.getPathByFileId(fileId);
    }

    @Override
    public Optional<Integer> countParagraphs(int id) {

        Optional<String> optText = storingGrpc.getFileTextById(id);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

        int pos = 0;
        int count = 0;
        int len = text.length();

        while (pos < len) {
            int newLine = 0;
            boolean hasWords = false;

            while (pos < len && text.charAt(pos) == '\n') {
                pos += 1;
            }
            while (pos < len && text.charAt(pos) != '\n') {
                pos += 1;
                hasWords = true;
            }
            while (pos < len && text.charAt(pos) == '\n') {
                pos += 1;
                newLine += 1;
            }

            if (hasWords && (newLine > 1 || pos == len)) {
                count += 1;
            }
        }
        return Optional.of(count);
    }

    @Override
    public Optional<Integer> countWords(int id) {

        Optional<String> optText = storingGrpc.getFileTextById(id);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

        int pos = 0;
        int count = 0;
        int len = text.length();

        while (pos < len) {
            boolean hasSep = false;
            boolean hasWord = false;

            while (pos < len && !Character.isLetter(text.charAt(pos))) {
                pos += 1;
            }
            while (pos < len && Character.isLetter(text.charAt(pos))) {
                pos += 1;
                hasWord = true;
            }
            while (pos < len && !Character.isLetter(text.charAt(pos))) {
                pos += 1;
                hasSep = true;
            }

            if (hasWord && (hasSep || pos == len)) {
                count += 1;
            }
        }
        return Optional.of(count);
    }

    @Override
    public Optional<Integer> countSymbols(int id) {

        Optional<String> optText = storingGrpc.getFileTextById(id);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

        return Optional.of(text.length());
    }


    // На перспективу
    private int cmpPair(String hash1, String hash2) {
        return (hash1 == hash2) ? 100 : 0;
    }

    // !!! Так как файлы уникальны, то всегда результатом будет 0 !!!
    @Override
    public Optional<Integer> calculatePlagiatePoints(int newFileId) {

        int points = 0;

        // Получаем список всех id-шников
        Optional<List<Integer>> optIds = storingGrpc.getAllIds();
        if (optIds.isEmpty()) {
            return Optional.empty();
        }
        List<Integer> ids = optIds.get();

        // Получаем хэш сравниваемого файла
        Optional<String> optNewFileHash = storingGrpc.getHashById(newFileId);
        if (optNewFileHash.isEmpty()) {
            return Optional.empty();
        }
        String newFileHash = optNewFileHash.get();

        // Проходимcя по списку всех id-шников
        for (int i = 0; i < ids.size(); ++i) {
            if (ids.get(i) == newFileId) { continue; }

            // Получаем хэш файла из хранилища
            Optional<String> optFileHash = storingGrpc.getHashById(ids.get(i));
            if (optFileHash.isEmpty()) {
                return Optional.empty();
            }
            String fileHash = optFileHash.get();

            // Определяем % схожести между текстами двух файлов
            points = max(points, cmpPair(fileHash, newFileHash));
        }

        return Optional.of(points);
    }

    @Override
    public Optional<byte[]> getWordCloudPic(int fileId) {
        Optional<String> path = getPathByFileId(fileId);
        if (path.isEmpty()) {
            return Optional.empty();
        }

        Path filePath = Path.of(path.get());
        if (!Files.exists(filePath)) {
            System.out.println("Файла не существует - TextAnalysService");
            return Optional.empty();
        }

        // Получаем байты изображения
        try {
            byte[] imageBytes = Files.readAllBytes(filePath);
            return Optional.of(imageBytes);
        } catch (Exception e) {
            System.out.println("Не удалось получить содержимое файла - TextAnalysService");
            return Optional.empty();
        }
    }
}
