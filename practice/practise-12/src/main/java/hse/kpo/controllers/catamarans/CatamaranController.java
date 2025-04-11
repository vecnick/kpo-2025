package hse.kpo.controllers.catamarans;

import java.util.List;
import hse.kpo.domains.Catamaran;
import hse.kpo.domains.cars.Car;
import hse.kpo.dto.request.CarRequest;
import hse.kpo.dto.request.CatamaranRequest;
import hse.kpo.enums.EngineTypes;
import hse.kpo.facade.Hse;
import hse.kpo.services.catamarans.HseCatamaranService;
import hse.kpo.storages.CatamaranStorage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catamarans")
@RequiredArgsConstructor
@Tag(name = "Катамараны", description = "Управление катамаранами")
public class CatamaranController {
    private final CatamaranStorage catamaranStorage;
    private final HseCatamaranService catamaranService;
    private final Hse hseFacade;

    @GetMapping("/{vin}")
    @Operation(summary = "Получить катамаран по VIN")
    public ResponseEntity<Catamaran> getCatamaranById(@PathVariable int vin) {
        return catamaranStorage.getCatamarans().stream()
            .filter(catamaran -> catamaran.getVin() == vin).findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать катамаран",
        description = "Для PEDAL требуется pedalSize (1-15)")
    public ResponseEntity<Catamaran> createCatamaran(
        @Valid @RequestBody CatamaranRequest request,
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

        var catamaran = switch (engineType.get()) {
            case EngineTypes.PEDAL -> hseFacade.addPedalCatamaran(request.pedalSize());
            case EngineTypes.HAND -> hseFacade.addHandCatamaran();
            case EngineTypes.LEVITATION -> hseFacade.addLevitationCatamaran();
            default -> throw new RuntimeException();
        };

        return ResponseEntity.status(HttpStatus.CREATED).body(catamaran);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продать все доступные катамараны")
    public ResponseEntity<Void> sellAllCatamarans() {
        catamaranService.sellCatamarans();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell/{vin}")
    @Operation(summary = "Продать катамаран по VIN")
    public ResponseEntity<Void> sellCatamaran(@PathVariable int vin) {
        var catamaranOptional = catamaranStorage.getCatamarans().stream()
            .filter(c -> c.getVin() == vin).findFirst();

        if (catamaranOptional.isPresent()) {
            var catamaran = catamaranOptional.get();
            catamaranStorage.getCatamarans().remove(catamaran);
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить катамаран")
    public ResponseEntity<Void> deleteCatamaran(@PathVariable int vin) {
        boolean removed = catamaranStorage.getCatamarans()
            .removeIf(catamaran -> catamaran.getVin() == vin);
        return removed ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получить все катамараны")
    public List<Catamaran> getAllCatamarans() {
        return catamaranStorage.getCatamarans();
    }
}