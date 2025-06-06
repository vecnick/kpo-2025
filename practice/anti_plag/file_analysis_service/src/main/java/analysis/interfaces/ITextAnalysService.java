package analysis.interfaces;

import analysis.entity.TextAnalys;

import java.util.List;
import java.util.Optional;

public interface ITextAnalysService {

    List<TextAnalys> getAll();
    Optional<TextAnalys> getByFileId(int fileId);
    Optional<String> getPathByFileId(int fileId);

    Optional<Integer> countParagraphs(int fileId);
    Optional<Integer> countWords(int fileId);
    Optional<Integer> countSymbols(int fileId);

    Optional<Integer> calculatePlagiatePoints(int newFileId);

    Optional<byte[]> getWordCloudPic(int fileId);
}
