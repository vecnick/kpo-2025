package storing.interfaces;

import org.springframework.web.multipart.MultipartFile;
import storing.record.FileUploadParams;


public interface IFileUploadService {

    FileUploadParams saveFile(MultipartFile file);
    boolean deleteFile(String filePath);
}
