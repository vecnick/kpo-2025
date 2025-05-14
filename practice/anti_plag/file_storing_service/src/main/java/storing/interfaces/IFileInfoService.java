package storing.interfaces;

import storing.entity.FileInfo;
import storing.record.FileInfoParams;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IFileInfoService {
    
    List<FileInfo> getAll();
    List<Integer> getAllIds();
    Optional<FileInfo> getById(int id);
    Optional<Integer> getIdByHash(String hash);
    Optional<String> getHashById(int id);
    Optional<String> getLocationById(int id);

    int saveFileInfo(FileInfoParams request);
    boolean deleteFileInfoById(int id);

    Optional<String> getFileTextById(int id) throws IOException;
}
