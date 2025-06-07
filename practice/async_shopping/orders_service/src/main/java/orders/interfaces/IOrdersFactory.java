package orders.interfaces;

import orders.entity.Order;

public interface IOrdersFactory {

    Order createOrder(int userId, int amount, String description);
}
