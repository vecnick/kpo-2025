package payments.interfaces;

import payments.entity.OutboxTask;
import payments.enums.DelayedTaskType;

public interface IOutboxTaskFactory {
    OutboxTask createOutboxTask(String requestPayload, DelayedTaskType taskType);
}
