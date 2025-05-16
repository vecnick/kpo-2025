package analysis.repository;

import analysis.entity.TextAnalys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TextAnalysRepository extends JpaRepository<TextAnalys, Integer> {

    // Дополнительные запросы помимо базовых

    @Query("""
            SELECT ta FROM TextAnalys ta 
            WHERE ta.fileId = :fileId
            """)
    Optional<TextAnalys> getByFileId(@Param("fileId") int fileId);

    @Query("""
            SELECT ta.WCPicPath FROM TextAnalys ta 
            WHERE ta.fileId = :fileId
            """)
    Optional<String> getPathByFileId(@Param("fileId") int fileId);
}