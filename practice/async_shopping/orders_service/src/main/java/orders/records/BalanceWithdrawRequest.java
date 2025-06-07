package orders.records;

import orders.entity.Order;

public record BalanceWithdrawRequest(
        int userId,
        int value
) {
    public BalanceWithdrawRequest(Order order) {
        this(order.userId, order.amount);
    }
}
