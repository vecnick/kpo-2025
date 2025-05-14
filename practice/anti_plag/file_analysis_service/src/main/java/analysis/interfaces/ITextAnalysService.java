package analysis.interfaces;

import java.util.Optional;

public interface ITextAnalysService {

    Optional<Integer> countParagraphs(int id);
    Optional<Integer> countWords(int id);
    Optional<Integer> countSymbols(int id);
}
