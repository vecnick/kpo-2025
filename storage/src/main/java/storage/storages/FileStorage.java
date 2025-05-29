package storage.storages;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import storage.domains.FileEntity;

public interface FileStorage extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> getFileById(Integer id);
    List<FileEntity> findAllByHash(String hash);
}