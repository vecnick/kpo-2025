package payments.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
            taskSubBalance(task);
        }
    }


    /*
    Примечание:
    С аннотацией @Transactional обеспечивается семантика exactly once (т.к. сообщение хранится
    внутри базы данных inbox_task и не меняет свой статус NEW, если происходит ошибка на каком-либо этапе.
    Если убрать аннотацию @Transactional, то благодаря установке статуса CANCELED перед началом всех других операций
    сообщение либо не будет обработано и будет отменено, либо обработается не более 1 раза.
     */
    @Transactional // откат если возникла ошибка (try catch не ставить!!)
    public void taskSubBalance(InboxTask task) {
        // помечаем запрос как отменённый для точного обеспечения семантики at most once (скорее всего будет откатано если произойдёт ошибка - но пусть будет для 100% уверенности)
        if (inboxTaskService.setStatusById(task.getId(), DelayedTaskStatus.CANCELED).isEmpty()) {
            throw new RuntimeException();
        }

        PaymentRequest request = JsonDeserializer.makeObjectFromJsonString(task.getRequestPayload(), PaymentRequest.class);

        Pair<BalanceAccount, BalanceAccountRequestResult> accountResult = paymentService.subBalanceByUserId(request.userId(), request.value());

        if (accountResult.getRight() == BalanceAccountRequestResult.FAIL) { // ошибка при обращении к сервису по уменьшению баланса
            throw new RuntimeException();
        } else if (accountResult.getRight() == BalanceAccountRequestResult.NOT_FOUND) {

            //  помечаем запрос как отменённый если userId неверный
            if (inboxTaskService.setStatusById(task.getId(), DelayedTaskStatus.CANCELED).isEmpty()) {
                throw new RuntimeException();
            }

            // отменяем заказ в сервисе order
            PaymentConfirmationRequest outboxRequest = new PaymentConfirmationRequest(request.orderId(), OrderStatus.CANCELLED);
            String outboxRequestStr = JsonSerializer.makeJsonString(outboxRequest);
            if (outboxTaskService.createOutboxTask(outboxRequestStr, DelayedTaskType.PAYMENT_CONFIRMATION).isEmpty()) {
                throw new RuntimeException();
            }
        } else if (accountResult.getRight() == BalanceAccountRequestResult.SUCCESS) {

            // подтверждаем уменьшение баланса
            if (inboxTaskService.setStatusById(task.getId(), DelayedTaskStatus.SENT).isEmpty()) {
                throw new RuntimeException();
            }

            // подтверждаем выполнение заказа в сервисе order
            PaymentConfirmationRequest outboxRequest = new PaymentConfirmationRequest(request.orderId(), OrderStatus.FINISHED);
            String outboxRequestStr = JsonSerializer.makeJsonString(outboxRequest);
            if (outboxTaskService.createOutboxTask(outboxRequestStr, DelayedTaskType.PAYMENT_CONFIRMATION).isEmpty()) {
                throw new RuntimeException();
            }
        }
    }
}
