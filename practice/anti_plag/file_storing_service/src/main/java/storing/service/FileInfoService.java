package storing.service;

import org.springframework.stereotype.Service;
import storing.entity.FileInfo;
import storing.interfaces.IFileInfoService;
import storing.record.FileInfoParams;
import storing.repository.FileInfoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileInfoService implements IFileInfoService {
    private final FileInfoRepository fileInfoRepository;

    public FileInfoService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    @Override
    public List<FileInfo> getAll() {
        return fileInfoRepository.findAll();
    }

    @Override
    public List<Integer> getAllIds() {
        return getAll().stream().map(FileInfo::getId).collect(Collectors.toList());
    }

    @Override
    public Optional<FileInfo> getById(int id) {
        return fileInfoRepository.findById(id);
    }

    @Override
    public Optional<Integer> getIdByHash(String hash) {
        return fileInfoRepository.getIdByHash(hash);
    }

    @Override
    public Optional<String> getHashById(int id) { return fileInfoRepository.getHashById(id); }

    @Override
    public Optional<String> getLocationById(int id) {
        return fileInfoRepository.getLocationById(id);
    }

    @Override
    public int saveFileInfo(FileInfoParams request) {
        Optional<Integer> existedFileId = getIdByHash(request.hash());
        if (existedFileId.isPresent()) { return existedFileId.get(); }

        FileInfo fileInfo = FileInfo.builder()
                .name(request.name())
                .hash(request.hash())
                .location(request.location())
                .build();
        return fileInfoRepository.save(fileInfo).getId();
    }

    @Override
    public boolean deleteFileInfoById(int id) {
        boolean inStorage = fileInfoRepository.existsById(id);
        fileInfoRepository.deleteById(id);
        return inStorage;
    }

    @Override
    public Optional<String> getFileTextById(int id) {

        // Получаем расположение файла
        Optional<String> location = getLocationById(id);
        if (location.isEmpty()) {
            return Optional.empty();
        }

        Path filePath = Path.of(location.get());
        if (!Files.exists(filePath)) {
            System.out.println("Файла не существует - FileInfoService");
            return Optional.empty();
        }

        // Читаем содержимое файла
        try {
            String content = Files.readString(filePath, StandardCharsets.UTF_8);
            return Optional.of(content);
        } catch (Exception e) {
            System.out.println("Не удалось получить содержимое файла - FileInfoService");
            return Optional.empty();
        }
    }
}
