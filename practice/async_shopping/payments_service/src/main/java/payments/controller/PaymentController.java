package payments.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.aspectj.apache.bcel.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payments.entity.BalanceAccount;
import payments.interfaces.IPaymentService;

import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Добавить счёт для пользователя")
    @PutMapping(value = "/accounts/create/{userId}")
    public ResponseEntity<BalanceAccount> createAccount(int userId) {
        return paymentService.createAccount(userId).map(
                account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Удалить счёт для пользователя")
    @DeleteMapping(value = "/accounts/delete/{userId}")
    public ResponseEntity<Void> deleteAccount(int userId) {
        return paymentService.deleteAccount(userId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Получить список всех счетов")
    @GetMapping(value = "/accounts/all")
    public ResponseEntity<List<BalanceAccount>> getAllAccounts() {
        return paymentService.getAllAccounts().map(
                accounts -> ResponseEntity.ok(accounts))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить баланс счёта")
    @GetMapping(value = "/accounts/balance/{userId}")
    public ResponseEntity<Integer> getBalanceByUserId(int userId) {
        return paymentService.getBalanceByUserId(userId).map(
                balance -> ResponseEntity.ok(balance))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Увеличить счёт для пользователя")
    @PostMapping(value = "/accounts/balance/add/{userId}{value}")
    public ResponseEntity<BalanceAccount> addBalanceByUserId(int userId, int value) {
        return paymentService.addBalanceByUserId(userId, value).map(
                account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Уменьшить счёт для пользователя")
    @PostMapping(value = "/accounts/balance/sub/{userId}{value}")
    public ResponseEntity<BalanceAccount> subBalanceByUserId(int userId, int value) {
        return paymentService.subBalanceByUserId(userId, value).map(
                account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.badRequest().build());
    }
}
