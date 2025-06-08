package payments.service;

import payments.entity.InboxTask;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;
import payments.factory.InboxTaskFactory;
import payments.interfaces.IInboxTaskService;
import payments.repository.InboxTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InboxTaskService implements IInboxTaskService {

    private final InboxTaskRepository inboxTaskRepository;
    private final InboxTaskFactory inboxTaskFactory;

    public InboxTaskService(InboxTaskRepository inboxTaskRepository, InboxTaskFactory inboxTaskFactory) {
        this.inboxTaskRepository = inboxTaskRepository;
        this.inboxTaskFactory = inboxTaskFactory;
    }

    @Override
    public Optional<InboxTask> createInboxTask(String requestPayload, DelayedTaskType taskType) {
        try {
            InboxTask inboxTask = inboxTaskFactory.createInboxTask(requestPayload, taskType);
            return Optional.of(inboxTaskRepository.save(inboxTask));
        } catch (Exception e) {
            System.out.println("InboxTaskService: createInboxTask: не удалось сохранить запрос");
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteInboxTask(int id) {
        try {
            boolean existed = inboxTaskRepository.existsById(id);
            inboxTaskRepository.deleteById(id);
            if (!existed) { System.out.println("InboxTaskService: deleteInboxTask: запрос с id=" + id + " не был найден"); }
            return existed;
        } catch (Exception e) {
            System.out.println("InboxTaskService: deleteInboxTask: не удалось удалить запрос в базе данных");
            return false;
        }
    }

    @Override
    public Optional<List<InboxTask>> getAllInboxTask() {
        try {
            return Optional.of(inboxTaskRepository.findAll());
        } catch (Exception e) {
            System.out.println("InboxTaskService: getAllInboxTask: не удалось получить список запросов базы данных");
            return Optional.empty();
        }
    }

    @Override
    public Optional<InboxTask> getInboxTask(int id) {
        try {
            return inboxTaskRepository.findById(id);
        } catch (Exception e) {
            System.out.println("InboxTaskService: getInboxTask: запрос с id=" + id + " не был найден");
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<InboxTask>> getInboxTasksByStatus(DelayedTaskStatus status) {
        try {
            return Optional.of(inboxTaskRepository.getInboxTasksByStatus(status));
        } catch (Exception e) {
            System.out.println("InboxTaskService: getInboxTasksByStatus: не удалось получить запросы по статусу" + e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<InboxTask> setStatusById(int id, DelayedTaskStatus status) {
        try {
            boolean notChanged = (0 == inboxTaskRepository.setStatusById(id, status));
            if (notChanged) {
                System.out.println("InboxTaskService: setStatusById: заказ с id=" + id + " не был найден");
                return Optional.empty();
            }
            return getInboxTask(id);
        } catch (Exception e) {
            System.out.println("InboxTaskService: setStatusById: не удалось установить статус для заказа в базе данных");
            return Optional.empty();
        }
    }
}
