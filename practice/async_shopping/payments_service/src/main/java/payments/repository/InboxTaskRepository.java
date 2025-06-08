package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import payments.entity.InboxTask;
import payments.enums.DelayedTaskStatus;

import java.util.List;

public interface InboxTaskRepository extends JpaRepository<InboxTask, Integer> {
    @Query("""
            SELECT it FROM InboxTask it
            WHERE it.status = :status
            """)
    List<InboxTask> getInboxTasksByStatus(DelayedTaskStatus status);

    @Transactional
    @Modifying
    @Query("""
            UPDATE InboxTask it
            SET it.status = :status
            WHERE it.id = :id
            """)
    int setStatusById(int id, DelayedTaskStatus status);
}