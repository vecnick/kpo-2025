package orders.factory;

import jakarta.persistence.Column;
import orders.entity.OutboxTask;
import orders.enums.OutboxStatus;
import orders.enums.TaskType;
import orders.interfaces.IOutboxTaskFactory;
import orders.utility.Date;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.stereotype.Component;

@Component
public class OutboxTaskFactory implements IOutboxTaskFactory {

    @Override
    public OutboxTask createOutboxTask(String requestPayload, TaskType taskType) {
        return OutboxTask.builder()
                .requestPayload(requestPayload)
                .status(OutboxStatus.NEW)
                .taskType(taskType)
                .createdDate(Date.getLocalDateTimeStr())
                .build();
    }
}