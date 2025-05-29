package storage.facades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storage.domains.FileEntity;
import storage.storages.FileStorage;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class FileFacade {
    private final FileStorage fileStorage;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    public FileEntity saveFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();

        String shortPath = uploadDirectory + "/" + UUID.randomUUID().toString() + filename;;

        Path path = Paths.get(shortPath);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        var hash = calculateFileHash(file.getBytes());

        FileEntity fileHeader = new FileEntity();
        fileHeader.setName(filename);
        fileHeader.setPath(shortPath);
        fileHeader.setHash(hash);
        fileHeader = fileStorage.save(fileHeader);

        return fileHeader;
    }

    public String getFileHash(Integer fileId) {
        var entity = fileStorage.getFileById(fileId);
        if (entity.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        return entity.get().getHash();
    }

    public String getContentById(Integer fileId) throws IOException {
        var entity = fileStorage.getFileById(fileId);
        if (entity.isEmpty()) {
            throw new IOException("File not found");
        }
        var path = Paths.get(entity.get().getPath());
        return FileUtil.readAsString(path.toFile());
    }

    public Optional<FileEntity> getFileById(Integer fileId) {
        return fileStorage.getFileById(fileId);
    }

    private String calculateFileHash(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content);
            return String.format("%064x", new BigInteger(1, hashBytes));
        } catch (NoSuchAlgorithmException e) {
            return "Invalid file, could not calculate hash";
        }
    }
}