package payments.factory;

import org.springframework.stereotype.Component;
import payments.entity.InboxTask;
import payments.entity.OutboxTask;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;
import payments.interfaces.IOutboxTaskFactory;
import payments.utility.Date;

@Component
public class OutboxTaskFactory implements IOutboxTaskFactory {

    @Override
    public OutboxTask createOutboxTask(String requestPayload, DelayedTaskType taskType) {
        return OutboxTask.builder()
                .requestPayload(requestPayload)
                .status(DelayedTaskStatus.NEW)
                .taskType(taskType)
                .createdDate(Date.getLocalDateTimeStr())
                .build();
    }
}
