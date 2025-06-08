package payments.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import payments.entity.BalanceAccount;
import payments.entity.InboxTask;
import payments.enums.BalanceAccountRequestResult;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;
import payments.enums.OrderStatus;
import payments.interfaces.IInboxTaskService;
import payments.interfaces.IOutboxTaskService;
import payments.interfaces.IPaymentService;
import payments.record.PaymentConfirmationRequest;
import payments.record.PaymentRequest;
import payments.utility.JsonDeserializer;
import payments.utility.JsonSerializer;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class InboxTaskOrderScheduler {
    
    private final IInboxTaskService inboxTaskService;
    private final IPaymentService paymentService;
    private final IOutboxTaskService outboxTaskService;

    public InboxTaskOrderScheduler(IInboxTaskService inboxTaskService, IPaymentService paymentService, IOutboxTaskService outboxTaskService) {
        this.inboxTaskService = inboxTaskService;
        this.paymentService = paymentService;
        this.outboxTaskService = outboxTaskService;
    }

    @Scheduled(fixedRate = 5000) // каждые 5 секунд
    public void receiveNewTasks() {
        Optional<List<InboxTask>> inboxTasks = inboxTaskService.getInboxTasksByStatus(DelayedTaskStatus.NEW);
        if (inboxTasks.isEmpty()) {
            System.out.println("InboxTaskScheduler: sendNewTasks: не удалось получить список задач");
            return;
        }

        for (InboxTask task : inboxTasks.get()) {
            try {
                PaymentRequest request = JsonDeserializer.makeObjectFromJsonString(task.getRequestPayload(), PaymentRequest.class);

                Pair<BalanceAccount, BalanceAccountRequestResult> accountResult = paymentService.subBalanceByUserId(request.userId(), request.value());
                if (accountResult.getRight() == BalanceAccountRequestResult.FAIL) {
                    throw new RuntimeException();
                } else if (accountResult.getRight() == BalanceAccountRequestResult.NOT_FOUND) {
                    inboxTaskService.setStatusById(task.getId(), DelayedTaskStatus.CANCELED); // отменяем запрос если userId неверный

                    // отменяем заказ в сервисе order
                    PaymentConfirmationRequest outboxRequest = new PaymentConfirmationRequest(request.orderId(), OrderStatus.CANCELLED);
                    String outboxRequestStr = JsonSerializer.makeJsonString(outboxRequest);
                    outboxTaskService.createOutboxTask(outboxRequestStr, DelayedTaskType.PAYMENT_CONFIRMATION);
                    throw new RuntimeException();
                }

                inboxTaskService.setStatusById(task.getId(), DelayedTaskStatus.SENT);

                // потверждаем выполнение заказа в сервисе order
                PaymentConfirmationRequest outboxRequest = new PaymentConfirmationRequest(request.orderId(), OrderStatus.FINISHED);
                String outboxRequestStr = JsonSerializer.makeJsonString(outboxRequest);
                outboxTaskService.createOutboxTask(outboxRequestStr, DelayedTaskType.PAYMENT_CONFIRMATION);

            } catch (Exception e) {
                System.out.println("InboxTaskScheduler: sendNewTasks: не удалось выполнить запрос с id=" + task.getId());
            }
        }
    }
}
