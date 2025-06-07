package orders.interfaces;

import orders.entity.OutboxTask;
import orders.enums.OutboxStatus;
import orders.enums.TaskType;

import java.util.List;
import java.util.Optional;

public interface IOutboxTaskService {
    
    Optional<OutboxTask> createOutboxTask(String requestPayload, TaskType taskType);
    boolean deleteOutboxTask(int id);

    Optional<List<OutboxTask>> getAllOutboxTask();
    Optional<OutboxTask> getOutboxTask(int id);
    Optional<List<OutboxTask>> getOutboxTasksByStatus(OutboxStatus status);

    Optional<OutboxTask> setStatusById(int id, OutboxStatus status);
}
