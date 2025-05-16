package hse.kpo.controllers.catamarans;

import java.util.List;
import java.util.Objects;
import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.dto.request.CatamaranRequest;
import hse.kpo.enums.EngineTypes;
import hse.kpo.facade.Hse;
import hse.kpo.services.catamarans.HseCatamaranService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
    private final HseCatamaranService catamaranService;
    private final Hse hseFacade;

    @GetMapping("/{vin}")
    @Operation(summary = "Получить катамаран по VIN")
    public ResponseEntity<Catamaran> getCatamaranById(@PathVariable int vin) {
        return catamaranService.getCatamarans().stream()
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
        var catamaranOptional = catamaranService.getCatamarans().stream()
            .filter(c -> c.getVin() == vin).findFirst();

        if (catamaranOptional.isPresent()) {
            var catamaran = catamaranOptional.get();
            catamaranService.getCatamarans().remove(catamaran);
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить катамаран")
    public ResponseEntity<Void> deleteCatamaran(@PathVariable int vin) {
        boolean removed = catamaranService.getCatamarans()
            .removeIf(catamaran -> catamaran.getVin() == vin);
        return removed ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получить все катамараны с фильтрацией",
        parameters = {
            @Parameter(name = "engineType", description = "Фильтр по типу двигателя"),
            @Parameter(name = "minVin", description = "Минимальный VIN")
        })
    public List<Catamaran> getAllCatamarans(
        @RequestParam(required = false) String engineType,
        @RequestParam(required = false) Integer minVin) {

        if (Objects.isNull(minVin)) {
            minVin = 0;
        }
        if (StringUtils.isBlank(engineType)) {
            return catamaranService.getCatamarans();
        }
        return catamaranService.getCatamaransWithFiltration(engineType, minVin);
    }
}