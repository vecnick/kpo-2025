package orders.service;

import orders.entity.Order;
import orders.enums.OrderStatus;
import orders.enums.TaskType;
import orders.interfaces.IOrderService;
import orders.interfaces.IOrdersFactory;
import orders.interfaces.IOutboxTaskService;
import orders.records.BalanceWithdrawRequest;
import orders.repository.OrderRepository;
import orders.utility.JsonSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IOrdersFactory ordersFactory;
    private final IOutboxTaskService outboxTaskService;

    public OrderService(OrderRepository orderRepository, IOrdersFactory ordersFactory, IOutboxTaskService outboxTaskService) {
        this.orderRepository = orderRepository;
        this.ordersFactory = ordersFactory;
        this.outboxTaskService = outboxTaskService;
    }

    @Transactional // откат и попытка отправки нового запроса при ошибке (try catch не ставить!!)
    @Override
    public Optional<Order> createOrder(int userId, int amount, String description) {
        // Сохраняем заказ в базу данных
        Order order = ordersFactory.createOrder(userId, amount, description);
        Order result = orderRepository.save(order);

        // Сохраняем запрос на списание баланса в базу данных, вместо того, чтобы отправлять напрямую
        BalanceWithdrawRequest request = new BalanceWithdrawRequest(result);
        String requestStr = JsonSerializer.makeJsonString(request);
        outboxTaskService.createOutboxTask(requestStr, TaskType.ORDER);

        return Optional.of(result);
    }

    @Override
    public boolean deleteOrder(int id) {
        try {
            boolean existed = orderRepository.existsById(id);
            orderRepository.deleteById(id);
            if (!existed) { System.out.println("OrderService: deleteOrder: заказ с id=" + id + " не был найден"); }
            return existed;
        } catch (Exception e) {
            System.out.println("OrderService: deleteOrder: не удалось удалить заказ в базе данных");
            return false;
        }
    }

    @Override
    public Optional<List<Order>> getAllOrders() {
        try {
            return Optional.of(orderRepository.findAll());
        } catch (Exception e) {
            System.out.println("OrderService: getAllOrders: не удалось получить список записей базы данных");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> getOrder(int id) {
        try {
            return orderRepository.findById(id);
        } catch (Exception e) {
            System.out.println("OrderService: getOrder: заказ с id=" + id + " не был найден");
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Order>> getOrdersByUserId(int userId) {
        try {
            return Optional.of(orderRepository.getOrdersByUserId(userId));
        } catch (Exception e) {
            System.out.println("OrderService: getStatusById: не удалось получить заказы по userId");
            return Optional.empty();
        }
    }

    @Override
    public Optional<OrderStatus> getStatusById(int id) {
        try {
            return orderRepository.getStatusById(id);
        } catch (Exception e) {
            System.out.println("OrderService: getStatusById: не удалось получить статус заказа по id");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getAmountById(int id) {
        try {
            return orderRepository.getAmountById(id);
        } catch (Exception e) {
            System.out.println("OrderService: getAmountById: не удалось получить сумму заказа по id");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> setStatusById(int id, OrderStatus status) {
        try {
            boolean notChanged = (0 == orderRepository.setStatusById(id, status));
            if (notChanged) {
                System.out.println("OrderService: setStatusById: заказ с id=" + id + " не был найден");
                return Optional.empty();
            }
            return getOrder(id);
        } catch (Exception e) {
            System.out.println("OrderService: setStatusById: не удалось установить статус для заказа в базе данных");
            return Optional.empty();
        }
    }
}
