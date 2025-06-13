package orders.controller;

import io.swagger.v3.oas.annotations.Operation;
import orders.entity.Order;
import orders.enums.OrderStatus;
import orders.interfaces.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Создать заказ")
    @PutMapping(value = "/orders/create/{userId}{amount}{description}")
    public ResponseEntity<Order> createOrder(int userId, int amount, @RequestParam(required = false) String description) {
        try {
            Optional<Order> order = orderService.createOrder(userId, amount, description);
            return ResponseEntity.ok(order.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Удалить заказ")
    @DeleteMapping(value = "/orders/delete/{id}")
    public ResponseEntity<Void> deleteOrder(int id) {
        return orderService.deleteOrder(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Получить все заказы заказ")
    @GetMapping(value = "/orders/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        return orderService.getAllOrders().map(
                orders -> ResponseEntity.ok(orders))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить заказ по его id")
    @GetMapping(value = "/orders/getById/{id}")
    public ResponseEntity<Order> getOrder(int id) {
        return orderService.getOrder(id).map(
                order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить все заказа пользователя по его id")
    @GetMapping(value = "/orders/getByUserId/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(int userId) {
        return orderService.getOrdersByUserId(userId).map(
                orders -> ResponseEntity.ok(orders))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить статус заказа по его id")
    @GetMapping(value = "/orders/getStatus/{id}")
    public ResponseEntity<OrderStatus> getStatusById(int id) {
        return orderService.getStatusById(id).map(
                status -> ResponseEntity.ok(status))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить сумму заказа по его id")
    @GetMapping(value = "/orders/getAmount/{id}")
    public ResponseEntity<Integer> getAmountById(int id) {
        return orderService.getAmountById(id).map(
                amount -> ResponseEntity.ok(amount))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Изменить статус заказа по его id")
    @PostMapping(value = "/orders/setStatus/{id}{status}")
    public ResponseEntity<Order> setStatusById(int id, OrderStatus status) {
        return orderService.setStatusById(id, status).map(
                order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.badRequest().build());
    }
}
