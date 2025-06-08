package payments.service;

import payments.entity.OutboxTask;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;
import payments.factory.OutboxTaskFactory;
import payments.interfaces.IOutboxTaskService;
import payments.repository.OutboxTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutboxTaskService implements IOutboxTaskService {

    private final OutboxTaskRepository outboxTaskRepository;
    private final OutboxTaskFactory outboxTaskFactory;

    public OutboxTaskService(OutboxTaskRepository outboxTaskRepository, OutboxTaskFactory outboxTaskFactory) {
        this.outboxTaskRepository = outboxTaskRepository;
        this.outboxTaskFactory = outboxTaskFactory;
    }

    @Override
    public Optional<OutboxTask> createOutboxTask(String requestPayload, DelayedTaskType taskType) {
        try {
            OutboxTask outboxTask = outboxTaskFactory.createOutboxTask(requestPayload, taskType);
            return Optional.of(outboxTaskRepository.save(outboxTask));
        } catch (Exception e) {
            System.out.println("OutboxTaskService: createOutboxTask: не удалось создать запрос");
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteOutboxTask(int id) {
        try {
            boolean existed = outboxTaskRepository.existsById(id);
            outboxTaskRepository.deleteById(id);
            if (!existed) { System.out.println("OutboxTaskService: deleteOutboxTask: запрос с id=" + id + " не был найден"); }
            return existed;
        } catch (Exception e) {
            System.out.println("OutboxTaskService: deleteOutboxTask: не удалось удалить запрос в базе данных");
            return false;
        }
    }

    @Override
    public Optional<List<OutboxTask>> getAllOutboxTask() {
        try {
            return Optional.of(outboxTaskRepository.findAll());
        } catch (Exception e) {
            System.out.println("OutboxTaskService: getAllOutboxTask: не удалось получить список запросов базы данных");
            return Optional.empty();
        }
    }

    @Override
    public Optional<OutboxTask> getOutboxTask(int id) {
        try {
            return outboxTaskRepository.findById(id);
        } catch (Exception e) {
            System.out.println("OutboxTaskService: getOutboxTask: запрос с id=" + id + " не был найден");
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<OutboxTask>> getOutboxTasksByStatus(DelayedTaskStatus status) {
        try {
            return Optional.of(outboxTaskRepository.getOutboxTasksByStatus(status));
        } catch (Exception e) {
            System.out.println("OutboxTaskService: getOutboxTasksByStatus: не удалось получить запросы по статусу" + e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<OutboxTask> setStatusById(int id, DelayedTaskStatus status) {
        try {
            boolean notChanged = (0 == outboxTaskRepository.setStatusById(id, status));
            if (notChanged) {
                System.out.println("OutboxTaskService: setStatusById: заказ с id=" + id + " не был найден");
                return Optional.empty();
            }
            return getOutboxTask(id);
        } catch (Exception e) {
            System.out.println("OutboxTaskService: setStatusById: не удалось установить статус для заказа в базе данных");
            return Optional.empty();
        }
    }
}
