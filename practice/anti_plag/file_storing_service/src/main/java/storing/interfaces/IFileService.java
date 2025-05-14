package storing.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    int save(MultipartFile file);
    boolean deleteById(int id);
}
