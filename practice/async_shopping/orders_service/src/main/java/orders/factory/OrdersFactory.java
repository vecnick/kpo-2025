package orders.factory;

import orders.entity.Order;
import orders.enums.OrderStatus;
import orders.interfaces.IOrdersFactory;
import orders.utility.Date;
import org.springframework.stereotype.Component;

@Component
public class OrdersFactory implements IOrdersFactory {

    @Override
    public Order createOrder(int userId, int amount, String description) {
        return Order.builder()
                .userId(userId)
                .amount(amount)
                .description(description)
                .status(OrderStatus.NEW)
                .createdDate(Date.getLocalDateTimeStr())
                .build();
    }
}
