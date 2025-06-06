package storing.interfaces;

import org.springframework.web.multipart.MultipartFile;
import storing.record.FileUploadParams;

import java.util.Optional;


public interface IFileUploadService {

    Optional<FileUploadParams> saveFile(MultipartFile file);
    boolean deleteFile(String filePath);
}
