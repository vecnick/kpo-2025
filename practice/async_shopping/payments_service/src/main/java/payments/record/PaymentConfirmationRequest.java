package payments.record;

import payments.enums.DelayedTaskStatus;
import payments.enums.OrderStatus;

public record PaymentConfirmationRequest(
        int orderId,
        OrderStatus status
) {
}
