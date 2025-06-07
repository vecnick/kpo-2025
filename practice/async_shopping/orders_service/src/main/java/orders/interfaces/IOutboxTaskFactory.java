package orders.interfaces;

import orders.entity.OutboxTask;
import orders.enums.TaskType;

public interface IOutboxTaskFactory {
    OutboxTask createOutboxTask(String requestPayload, TaskType taskType);
}
