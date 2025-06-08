package orders.interfaces;

import orders.enums.OrderStatus;

public interface IInboxTaskOrderConfirmationService {
    void confirmOrder(String jsonRequest);
}
