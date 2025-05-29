package analysis.repositories;

import analysis.domains.StatisticEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<StatisticEntity, Integer> {
    Optional<StatisticEntity> getByFileId(Integer fileId);
}