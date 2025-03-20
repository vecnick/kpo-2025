package hse.kpo.Controllers;

import hse.kpo.Enums.EngineTypes;
import hse.kpo.Facades.Hse;
import hse.kpo.Requests.CarRequest;
import hse.kpo.Requests.ShipRequest;
import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Engines.HandEngineI;
import hse.kpo.domains.Engines.LevitatingEngineI;
import hse.kpo.domains.Engines.PedalEngineI;
import hse.kpo.domains.Ships.Ship;
import hse.kpo.domains.params.EmptyEngineParams;
import hse.kpo.factories.Ships.ShipFactory;
import hse.kpo.interfaces.EngineI;
import hse.kpo.services.CarServiceI;
import hse.kpo.services.HseCarService;
import hse.kpo.services.HseShipService;
import hse.kpo.services.ShipService;
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

@RestController
@RequestMapping("/api/ships")
@RequiredArgsConstructor
@Tag(name = "Корабли", description = "Управление кораблями")
public class ShipController {
    private final CarServiceI carStorage;
    private final HseCarService carService;
    private final Hse hseFacade;
    private final ShipService shipService;
    private final Hse hse;
    private final ShipFactory shipFactory;
    private final HseShipService hseShipService;

    private Ship createShipFromRequest(ShipRequest request, int vin) {
        EngineI engine = new HandEngineI();
        return new Ship(vin, engine);
    }

    @GetMapping("/{vin}")
    @Operation(summary = "Получить катамаран по VIN")
    public ResponseEntity<Ship> getShipByVin(@PathVariable int vin) {
        return shipService.getShips().stream()
                .filter(car -> car.getVin() == vin)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать корабль")
    public ResponseEntity<Ship> createShip(
            @Valid @RequestBody ShipRequest request,
            BindingResult bindingResult) {
        System.out.println(request);
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        var ship = shipService.addCar(shipFactory, new EmptyEngineParams());
        return ResponseEntity.status(HttpStatus.CREATED).body(ship);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продать все доступные корабли")
    public ResponseEntity<Void> sellAllShip() {
        hseShipService.sellCars();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell/{vin}")
    @Operation(summary = "Продать корабль по VIN")
    public ResponseEntity<Void> sellShip(@PathVariable int vin) {
        var carOptional = shipService.getShips().stream()
                .filter(c -> c.getVin() == vin)
                .findFirst();

        if (carOptional.isPresent()) {
            var car = carOptional.get();
            carStorage.getCars().remove(car);
            // Логика продажи (упрощенно)
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{vin}")
    @Operation(summary = "Обновить корабль")
    public ResponseEntity<Ship> updateShip(
            @PathVariable int vin,
            @Valid @RequestBody ShipRequest request) {

        return shipService.getShips().stream()
                .filter(car -> car.getVin() == vin)
                .findFirst()
                .map(existingCar -> {
                    var updatedCar = createShipFromRequest(request, vin);
                    shipService.getShips().remove(existingCar);
                    shipService.addExistingShip(updatedCar);
                    return ResponseEntity.ok(updatedCar);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить корабль")
    public ResponseEntity<Void> deleteShip(@PathVariable int vin) {
        boolean removed = shipService.getShips().removeIf(car -> car.getVin() == vin);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}