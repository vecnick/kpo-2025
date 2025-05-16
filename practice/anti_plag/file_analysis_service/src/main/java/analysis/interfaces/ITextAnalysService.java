package analysis.interfaces;

import java.util.Optional;

public interface ITextAnalysService {

    Optional<Integer> countParagraphs(int fileId);
    Optional<Integer> countWords(int fileId);
    Optional<Integer> countSymbols(int fileId);

    Optional<Integer> calculatePlagiatePoints(int newFileId);
}
