package analysis.interfaces;

import storing.interfaces.IFileInfoService;

import java.util.List;
import java.util.Optional;

public interface IFileInfoServiceMediator {

    void setService(IFileInfoService fileInfoService);

    Optional<List<Integer>> getAllIds();
    Optional<String> getHashById(int id);
    Optional<String> getFileTextById(int id);
}
