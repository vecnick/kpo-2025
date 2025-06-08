package payments.interfaces;

import payments.entity.InboxTask;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;

import java.util.List;
import java.util.Optional;

public interface IInboxTaskService {
    
    Optional<InboxTask> createInboxTask(String requestPayload, DelayedTaskType taskType);
    boolean deleteInboxTask(int id);

    Optional<List<InboxTask>> getAllInboxTask();
    Optional<InboxTask> getInboxTask(int id);
    Optional<List<InboxTask>> getInboxTasksByStatus(DelayedTaskStatus status);

    Optional<InboxTask> setStatusById(int id, DelayedTaskStatus status);
}
