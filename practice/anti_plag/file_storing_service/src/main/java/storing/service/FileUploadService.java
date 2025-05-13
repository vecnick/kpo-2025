package storing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storing.record.FileUploadParams;
import storing.util.DateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

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
            String dateFilename = FilenameUtils.getBaseName(filename)
                    + "_"
                    + DateUtil.getLocalDateTimeStr();
            dateFilename += (FilenameUtils.getExtension(filename) != "")
                    ? ("." + FilenameUtils.getExtension(filename)) // у файла есть расширение
                    : (""); // у файла нет расширения

            // Сохраняем файл
            Path destination = filesDir.resolve(dateFilename);
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
