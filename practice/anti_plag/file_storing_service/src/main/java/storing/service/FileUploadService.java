package storing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storing.record.FileUploadParams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    private final Path filesDir;

    public FileUploadService(Path filesDir) {
        this.filesDir = filesDir;
    }

    public FileUploadParams saveFile(MultipartFile file) {
        try {
            // Создаём папку, если её нет
            Files.createDirectories(filesDir);

            // Генерируем уникальное имя файла
            String filename = file.getOriginalFilename();
            Path destination = filesDir.resolve(filename);

            // Сохраняем файл
            file.transferTo(destination.toFile());

            return new FileUploadParams(destination.toString(), filename);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл", e);
        }
    }

    public boolean deleteFile(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось удалить файл по пути: " + filePath, e);
        }
    }
}
