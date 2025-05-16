package analysis.interfaces;

import analysis.entity.TextAnalys;

import java.util.List;
import java.util.Optional;

public interface IAnalysService {

    List<TextAnalys> getAll();
    Optional<TextAnalys> getByFileId(int fileId);
    Optional<String> getPathByFileId(int fileId);

    Optional<TextAnalys> startAnalys(int fileId);
}
