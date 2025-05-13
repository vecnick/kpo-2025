package storing.service;

import org.springframework.stereotype.Service;
import storing.entity.FileInfo;
import storing.record.FileInfoParams;
import storing.repository.FileInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FileInfoService {
    private final FileInfoRepository fileInfoRepository;

    public FileInfoService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public List<FileInfo> getAll() {
        return  fileInfoRepository.findAll();
    }

    public Optional<FileInfo> getById(int id) {
        return fileInfoRepository.findById(id);
    }

    public Optional<Integer> getIdByHash(String hash) {
        return fileInfoRepository.getIdByHash(hash);
    }

    public Optional<String> getLocationById(int id) {
        return fileInfoRepository.getLocationById(id);
    }

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

    public boolean deleteFileInfoById(int id) {
        boolean inStorage = fileInfoRepository.existsById(id);
        fileInfoRepository.deleteById(id);
        return inStorage;
    }
}
