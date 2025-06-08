package payments.interfaces;

import payments.entity.InboxTask;
import payments.enums.DelayedTaskType;

public interface IInboxTaskFactory {
    InboxTask createInboxTask(String requestPayload, DelayedTaskType taskType);
}
