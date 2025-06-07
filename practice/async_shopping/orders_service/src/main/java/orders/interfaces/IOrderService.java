package orders.interfaces;

import orders.entity.Order;
import orders.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    Optional<Order> createOrder(int userId, int amount, String description);
    boolean deleteOrder(int id);

    Optional<List<Order>> getAllOrders();
    Optional<Order> getOrder(int id);
    Optional<List<Order>> getOrdersByUserId(int userId);
    Optional<OrderStatus> getStatusById(int id);
    Optional<Integer> getAmountById(int id);

    Optional<Order> setStatusById(int id, OrderStatus status);
}
