package hse.kpo.controllers;

import java.util.List;
import java.util.stream.Collectors;
import hse.kpo.domains.Customer;
import hse.kpo.kafka.TrainingCompletedEvent;
import hse.kpo.repositories.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = " Тренировочный центр", description = "Тренировка покупателей")
public class TrainingController {
    @Autowired
    private KafkaTemplate<String, TrainingCompletedEvent> kafkaTemplate;

    @Autowired
    private CustomerRepository repository;

    @PostMapping("/train/{customerId}")
    public ResponseEntity<String> trainCustomer(@PathVariable int customerId,
                                                @RequestParam String trainType) {
        var customer = repository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setHandPower(customer.getHandPower() + 1);

        // Отправляем обновлённые данные обратно в Sales Service
        TrainingCompletedEvent trainingCompletedEvent = new TrainingCompletedEvent(
            customerId, "handPower"
        );
        kafkaTemplate.send("training-updates", trainingCompletedEvent);

        return ResponseEntity.ok("Тренировка завершена! Параметры обновлены");
    }

    @GetMapping
    @Operation(summary = "Получить всех клиентов")
    public List<CustomerResponse> getAllCustomers() {
        return repository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getName(),
            customer.getLegPower(),
            customer.getHandPower(),
            customer.getIq()
        );
    }
}
