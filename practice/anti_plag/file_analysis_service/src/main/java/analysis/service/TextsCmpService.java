package analysis.service;

import analysis.interfaces.IFileInfoServiceMediator;
import analysis.interfaces.ITextsCmpService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;

@Service
public class TextsCmpService implements ITextsCmpService {

    private final IFileInfoServiceMediator fileInfoServiceMediator;

    public TextsCmpService(IFileInfoServiceMediator fileInfoServiceMediator) {
        this.fileInfoServiceMediator = fileInfoServiceMediator;
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
        Optional<List<Integer>> optIds = fileInfoServiceMediator.getAllIds();
        if (optIds.isEmpty()) {
            return Optional.empty();
        }
        List<Integer> ids = optIds.get();

        // Получаем хэш сравниваемого файла
        Optional<String> optNewFileHash = fileInfoServiceMediator.getHashById(newFileId);
        if (optNewFileHash.isEmpty()) {
            return Optional.empty();
        }
        String newFileHash = optNewFileHash.get();

        // Проходимcя по списку всех id-шников
        for (int i = 0; i < ids.size(); ++i) {
            if (ids.get(i) == newFileId) { continue; }

            // Получаем хэш файла из хранилища
            Optional<String> optFileHash = fileInfoServiceMediator.getHashById(ids.get(i));
            if (optFileHash.isEmpty()) {
                return Optional.empty();
            }
            String fileHash = optFileHash.get();

            // Определяем % схожести между текстами двух файлов
            points = max(points, cmpPair(fileHash, newFileHash));
        }

        return Optional.of(points);
    }
}
