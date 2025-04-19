package hse.kpo.Controllers;

import hse.kpo.Enums.EngineTypes;
import hse.kpo.Facades.Hse;
import hse.kpo.Requests.CarRequest;
import hse.kpo.Requests.CustomerRequest;
import hse.kpo.Response.CustomerResponse;
import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.domains.Engines.HandEngineI;
import hse.kpo.domains.Engines.LevitatingEngineI;
import hse.kpo.domains.Engines.PedalEngineI;
import hse.kpo.services.CustomerStorageI;
import hse.kpo.services.HseCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Клиенты", description = "Управление клиентами")
public class CustomerController {
    private final Hse hseFacade;
    private final CustomerStorageI customerStorage;

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
        customerStorage.updateCustomer(updatedCustomer);
        return ResponseEntity.ok(convertToResponse(updatedCustomer));
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить клиента")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String name) {
        customerStorage.deleteCustomer(name);
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
                customer.getCars() != null && customer.getCars().size() > 0  ? customer.getCars().get(0).getVin() : null,
                customer.getShip() != null ? customer.getShip().getVin() : null
        );
    }
}