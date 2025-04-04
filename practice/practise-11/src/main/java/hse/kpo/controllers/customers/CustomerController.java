package hse.kpo.controllers.customers;

import hse.kpo.domains.Customer;
import hse.kpo.dto.request.CustomerRequest;
import hse.kpo.dto.response.CustomerResponse;
import hse.kpo.facade.Hse;
import hse.kpo.storages.CustomerStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Клиенты", description = "Управление клиентами")
public class CustomerController {
    private final Hse hseFacade;
    private final CustomerStorage customerStorage;

    @PostMapping
    @Operation(summary = "Создать клиента")
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {
        hseFacade.addCustomer(request.getName(),
                request.getLegPower(),
                request.getHandPower(),
                request.getIq());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToResponse(findCustomerByName(request.getName())));
    }

    @PutMapping("/{name}")
    @Operation(summary = "Обновить клиента")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String name,
            @Valid @RequestBody CustomerRequest request) {
        Customer updatedCustomer = Customer.builder()
                .name(name)
                .legPower(request.getLegPower())
                .handPower(request.getHandPower())
                .iq(request.getIq())
                .build();
        hseFacade.updateCustomer(updatedCustomer);
        return ResponseEntity.ok(convertToResponse(updatedCustomer));
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить клиента")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String name) {
        hseFacade.deleteCustomer(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Получить всех клиентов")
    public List<CustomerResponse> getAllCustomers() {
        return customerStorage.getCustomers().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private Customer findCustomerByName(String name) {
        return customerStorage.getCustomers().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getName(),
                customer.getLegPower(),
                customer.getHandPower(),
                customer.getIq(),
                customer.getCar() != null ? customer.getCar().getVin() : null,
                customer.getCatamaran() != null ? customer.getCatamaran().getVin() : null
        );
    }
}
