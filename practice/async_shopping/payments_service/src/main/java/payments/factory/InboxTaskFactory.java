package payments.factory;

import payments.entity.InboxTask;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;
import payments.interfaces.IInboxTaskFactory;
import payments.utility.Date;
import org.springframework.stereotype.Component;

@Component
public class InboxTaskFactory implements IInboxTaskFactory {

    @Override
    public InboxTask createInboxTask(String requestPayload, DelayedTaskType taskType) {
        return InboxTask.builder()
                .requestPayload(requestPayload)
                .status(DelayedTaskStatus.NEW)
                .taskType(taskType)
                .createdDate(Date.getLocalDateTimeStr())
                .build();
    }
}