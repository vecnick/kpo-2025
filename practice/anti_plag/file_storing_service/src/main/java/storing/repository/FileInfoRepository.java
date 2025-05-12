package storing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import storing.entity.FileInfo;

import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {

    // Дополнительные запросы помимо базовых

    @Query("""
            SELECT fi.id FROM FileInfo fi 
            WHERE fi.hash = :hash
            """)
    Optional<Integer> getIdByHash(@Param("hash") String hash);

    @Query("""
            SELECT fi.location FROM FileInfo fi 
            WHERE fi.id = :id
            """)
    Optional<String> getLocationById(@Param("id") Integer id);

}
