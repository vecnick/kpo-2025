package storing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storing.entity.FileInfo;
import storing.interfaces.IFileInfoService;
import storing.interfaces.IFileService;
import storing.interfaces.IFileUploadService;
import storing.record.FileInfoParams;
import storing.record.FileUploadParams;
import storing.util.FileHashUtil;

import java.util.Optional;

@Service
public class FileService implements IFileService {
    private final IFileInfoService fileInfoService;
    private final IFileUploadService fileUploadService;

    public FileService(IFileInfoService fileInfoService, IFileUploadService fileUploadService) {
        this.fileInfoService = fileInfoService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public int save(MultipartFile file) {
        // Вычисляем хэш файла
        String fileHash = FileHashUtil.calculateSha256(file);

        // Проверяем существует ли уже файл с таким хэшем
        Optional<Integer> existedFileId = fileInfoService.getIdByHash(fileHash);
        if (existedFileId.isPresent()) { return existedFileId.get(); }

        // Загружаем файл в локальное хранилище
        FileUploadParams fileUploadParams = fileUploadService.saveFile(file);

        // Получаем информацию о файле
        FileInfoParams fileInfoParams = FileInfoParams.builder()
                .name(fileUploadParams.name())
                .hash(fileHash)
                .location(fileUploadParams.path())
                .build();

        // Сохраняем информацию в базу данных и возвращаем её id
        return fileInfoService.saveFileInfo(fileInfoParams);
    }

    @Override
    public boolean deleteById(int id) {

        // Получаем информацию о файле
        Optional<FileInfo> existedFile = fileInfoService.getById(id);
        if (existedFile.isEmpty()) {
            return false;
        }
        FileInfoParams fileInfoParams = new FileInfoParams(existedFile.get());

        // Удаляем информацию из базы данных
        if (!fileInfoService.deleteFileInfoById(id)) {
            return false;
        }

        // Удаляем информацию из хранилища
        return fileUploadService.deleteFile(fileInfoParams.location());
    }
}
