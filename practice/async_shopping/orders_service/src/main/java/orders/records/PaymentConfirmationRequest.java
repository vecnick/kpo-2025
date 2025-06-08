package orders.records;

import orders.enums.OrderStatus;

public record PaymentConfirmationRequest(
        int orderId,
        OrderStatus status
) {
}
