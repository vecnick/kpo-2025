package payments.interfaces;

import payments.entity.OutboxTask;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;

import java.util.List;
import java.util.Optional;

public interface IOutboxTaskService {
    
    Optional<OutboxTask> createOutboxTask(String requestPayload, DelayedTaskType taskType);
    boolean deleteOutboxTask(int id);

    Optional<List<OutboxTask>> getAllOutboxTask();
    Optional<OutboxTask> getOutboxTask(int id);
    Optional<List<OutboxTask>> getOutboxTasksByStatus(DelayedTaskStatus status);

    Optional<OutboxTask> setStatusById(int id, DelayedTaskStatus status);
}
