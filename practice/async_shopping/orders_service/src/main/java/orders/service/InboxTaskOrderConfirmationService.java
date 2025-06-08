package orders.service;

import orders.enums.OrderStatus;
import orders.interfaces.IInboxTaskOrderConfirmationService;
import orders.interfaces.IOrderService;
import orders.records.PaymentConfirmationRequest;
import orders.utility.JsonDeserializer;
import org.springframework.stereotype.Service;

@Service
public class InboxTaskOrderConfirmationService implements IInboxTaskOrderConfirmationService {

    private final IOrderService orderService;

    public InboxTaskOrderConfirmationService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void confirmOrder(String jsonRequest) {
        PaymentConfirmationRequest request = JsonDeserializer.makeObjectFromJsonString(jsonRequest, PaymentConfirmationRequest.class);
        orderService.setStatusById(request.orderId(), request.status());
    }
}
