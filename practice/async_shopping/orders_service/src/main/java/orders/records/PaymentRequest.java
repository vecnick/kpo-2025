package orders.records;

import orders.entity.Order;

public record PaymentRequest(
        int orderId,
        int userId,
        int value
) {
    public PaymentRequest(Order order) {
        this(order.getId(), order.getUserId(), order.getAmount());
    }
}
