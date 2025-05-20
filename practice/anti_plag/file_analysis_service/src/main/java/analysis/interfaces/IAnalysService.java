package analysis.interfaces;

import analysis.entity.TextAnalys;

import java.util.List;
import java.util.Optional;

public interface IAnalysService {

    Optional<TextAnalys> startAnalys(int fileId);
    boolean deleteAnalys(int fileId);
}
