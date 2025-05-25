package analysis.service;

import analysis.entity.TextAnalys;
import analysis.interfaces.IAnalysService;
import analysis.interfaces.ITextAnalysService;
import analysis.interfaces.IWordCloudService;
import analysis.record.WordCloudPicParams;
import analysis.repository.TextAnalysRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnalysService implements IAnalysService {
    TextAnalysRepository textAnalysRepository;
    ITextAnalysService textAnalysService;
    IWordCloudService wordCloudService;

    public AnalysService(TextAnalysRepository textAnalysRepository, ITextAnalysService textAnalysService, IWordCloudService wordCloudService) {
        this.textAnalysRepository = textAnalysRepository;
        this.textAnalysService = textAnalysService;
        this.wordCloudService = wordCloudService;
    }

    @Override
    public Optional<TextAnalys> startAnalys(int fileId) {

        // Возвращаем уже имеющиеся записи, если такие есть
        Optional<TextAnalys> existedTextAnalys = textAnalysService.getByFileId(fileId);
        if (existedTextAnalys.isPresent()) {
            return existedTextAnalys;
        }

        Optional<Integer> paragraphs = textAnalysService.countParagraphs(fileId);
        Optional<Integer> words = textAnalysService.countWords(fileId);
        Optional<Integer> symbols = textAnalysService.countSymbols(fileId);
        Optional<Integer> plagiatePoints = textAnalysService.calculatePlagiatePoints(fileId);
        Optional<WordCloudPicParams> wordCloudPicParams = wordCloudService.createWordCloud(fileId);

        if (paragraphs.isEmpty() || words.isEmpty() || symbols.isEmpty() || plagiatePoints.isEmpty() || wordCloudPicParams.isEmpty()) {
            System.out.println("В FullAnalysService не все параметры корректно поулчены.");
            return Optional.empty();
        }

        TextAnalys textAnalys = TextAnalys.builder()
                .paragraphs(paragraphs.get())
                .words(words.get())
                .symbols(symbols.get())
                .plagiatePoints(plagiatePoints.get())
                .WCPicName(wordCloudPicParams.get().name())
                .WCPicPath(wordCloudPicParams.get().path())
                .fileId(fileId)
                .build();

        textAnalysRepository.save(textAnalys);

        return Optional.of(textAnalys);
    }

    @Override
    public boolean deleteAnalys(int fileId) {
        // Удаляем файл
        Optional<String> picPath = textAnalysService.getPathByFileId(fileId);
        if (picPath.isEmpty() || !wordCloudService.deleteWordCloud(picPath.get())) {
            return false;
        }

        Optional<Integer> analysId = textAnalysRepository.getIdByFileId(fileId);
        if (analysId.isEmpty()) {
            return false;
        }

        // Удаляем запись из бд
        boolean inStorage = textAnalysRepository.existsById(analysId.get());
        textAnalysRepository.deleteById(analysId.get());
        return inStorage;
    }
}
