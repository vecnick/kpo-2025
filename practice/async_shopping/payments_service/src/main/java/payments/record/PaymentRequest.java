package payments.record;

public record PaymentRequest(
        int orderId,
        int userId,
        int value
) {
}
