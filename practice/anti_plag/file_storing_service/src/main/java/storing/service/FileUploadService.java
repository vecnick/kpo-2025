package storing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storing.interfaces.IFileUploadService;
import storing.record.FileUploadParams;
import storing.util.FileDateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;

@Service
public class FileUploadService implements IFileUploadService {

    private final Path filesDir;

    public FileUploadService(Path filesDir) {
        this.filesDir = filesDir;
    }

    @Override
    public Optional<FileUploadParams> saveFile(MultipartFile file) {

        // Создаём папку, если её нет
        try {
            Files.createDirectories(filesDir);
        } catch (Exception e) {
            System.out.println("Не удалось создать директорию - FileUploadService");
            return Optional.empty();
        }

        // Генерируем уникальное имя файла
        String filename = file.getOriginalFilename();
        String dateFilename = FilenameUtils.getBaseName(filename)
                + "_"
                + FileDateUtil.getLocalDateTimeStr();
        dateFilename += (FilenameUtils.getExtension(filename) != "")
                ? ("." + FilenameUtils.getExtension(filename)) // у файла есть расширение
                : (""); // у файла нет расширения

        // Сохраняем файл
        Path destination = filesDir.resolve(dateFilename);
        try {
            file.transferTo(destination.toFile());
        } catch (Exception e) {
            System.out.println("Не удалось сохранить файл -FileUploadService");
            return Optional.empty();
        }

        return Optional.of(new FileUploadParams(destination.toString(), filename));
    }

    @Override
    public boolean deleteFile(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Не удалось удалить файл - FileUploadService");
            return false;
        }
    }
}
