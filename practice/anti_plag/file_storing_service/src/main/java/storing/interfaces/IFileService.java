package storing.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IFileService {
    Optional<Integer> save(String location, String text);
    Optional<Integer> save(MultipartFile file);
    boolean deleteById(int id);
}
