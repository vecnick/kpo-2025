package zooapi.zooerp2.Presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zooapi.zooerp2.Domain.DTO.Requests.EnclosureRequest;
import zooapi.zooerp2.Domain.Entities.Enclosure;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.Interfaces.Application.EnclosureServiceI;
import zooapi.zooerp2.Domain.ValueObjects.AnimalNumber;
import zooapi.zooerp2.Domain.ValueObjects.Size3D;

import java.util.UUID;

@RestController
@RequestMapping("/api/enclosures")
@RequiredArgsConstructor
@Tag(name = "Вольеры", description = "Управление вольерами")
public class EnclosureController {
    private final EnclosureServiceI enclosureService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить вольер по Id")
    public ResponseEntity<Enclosure> getCarByVin(@PathVariable int id) {
        return enclosureService.getEnclosure(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вольер")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        enclosureService.deleteEnclosure(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "Создать вольер",
            description = "")
    public ResponseEntity<Enclosure> createCar(
            @Valid @RequestBody EnclosureRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        var animalType = AnimalType.find(request.getAnimalType());
        if (animalType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No such animal type");
        }

        var size = new Size3D(request.getWidth(), request.getDepth(), request.getHeight());
        var maxN = new AnimalNumber(request.getMaxAnimalNumber());

        var ret = enclosureService.addEnclosure(animalType.orElseThrow(), size, maxN);

        if (ret.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(ret.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
