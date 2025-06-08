package payments.repository;

import payments.entity.OutboxTask;
import payments.enums.DelayedTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OutboxTaskRepository extends JpaRepository<OutboxTask, Integer> {
    @Query("""
            SELECT ot FROM OutboxTask ot
            WHERE ot.status = :status
            """)
    List<OutboxTask> getOutboxTasksByStatus(DelayedTaskStatus status);

    @Transactional
    @Modifying
    @Query("""
            UPDATE OutboxTask ot
            SET ot.status = :status
            WHERE ot.id = :id
            """)
    int setStatusById(int id, DelayedTaskStatus status);
}
