package hse.kpo.controllers.cars;

import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.HandEngine;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.domains.PedalEngine;
import hse.kpo.domains.cars.Car;
import hse.kpo.dto.request.CarRequest;
import hse.kpo.enums.EngineTypes;
import hse.kpo.facade.Hse;
import hse.kpo.services.cars.HseCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили", description = "Управление транспортными средствами")
public class CarController {
    private final HseCarService carService;
    private final Hse hseFacade;

    @GetMapping("/{vin}")
    @Operation(summary = "Получить автомобиль по VIN")
    public ResponseEntity<Car> getCarByVin(@PathVariable int vin) {
        return carService.findByVin(vin)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать автомобиль",
            description = "Для PEDAL требуется pedalSize (1-15)")
    public ResponseEntity<Car> createCar(
            @Valid @RequestBody CarRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        var engineType = EngineTypes.find(request.engineType());
        if (engineType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No this type");
        }

        var car = switch (engineType.get()) {
            case EngineTypes.PEDAL -> hseFacade.addPedalCar(request.pedalSize());
            case EngineTypes.HAND -> hseFacade.addHandCar();
            case EngineTypes.LEVITATION -> hseFacade.addLevitationCar();
            default -> throw new RuntimeException();
        };

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продать все доступные автомобили")
    public ResponseEntity<Void> sellAllCars() {
        carService.sellCars();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell/{vin}")
    @Operation(summary = "Продать автомобиль по VIN")
    public ResponseEntity<Object> sellCar(@PathVariable int vin) {
        return carService.findByVin(vin).map(car -> {
            carService.deleteByVin(car.getVin());
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{vin}")
    @Operation(summary = "Обновить автомобиль")
    public ResponseEntity<Car> updateCar(
            @PathVariable int vin,
            @Valid @RequestBody CarRequest request) {

        return carService.findByVin(vin)
                .map(existingCar -> {
                    existingCar.setEngine(createEngineFromRequest(request));
                    carService.addExistingCar(existingCar);
                    return ResponseEntity.ok(existingCar);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить автомобиль")
    public ResponseEntity<Void> deleteCar(@PathVariable int vin) {
        carService.deleteByVin(vin);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Получить все автомобили с фильтрацией",
            parameters = {
                    @Parameter(name = "engineType", description = "Фильтр по типу двигателя"),
                    @Parameter(name = "minVin", description = "Минимальный VIN")
            })
    public List<Car> getAllCars(
            @RequestParam(required = false) String engineType,
            @RequestParam(required = false) Integer minVin) {
        if (Objects.isNull(minVin)) {
            minVin = 0;
        }
        if (StringUtils.isBlank(engineType)) {
            return carService.getCars();
        }
        return carService.getCarsWithFiltration(engineType, minVin);
    }

    private AbstractEngine createEngineFromRequest(CarRequest request) {
        return switch (EngineTypes.valueOf(request.engineType())) {
            case PEDAL -> new PedalEngine(request.pedalSize());
            case HAND -> new HandEngine();
            case LEVITATION -> new LevitationEngine();
        };
    }
}