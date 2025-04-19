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
import zooapi.zooerp2.Domain.DTO.Requests.AnimalRequest;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.Enums.FoodType;
import zooapi.zooerp2.Domain.Enums.Sex;
import zooapi.zooerp2.Domain.Interfaces.Application.AnimalServiceI;
import zooapi.zooerp2.Domain.ValueObjects.Name;

import java.util.UUID;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
@Tag(name = "Животные", description = "Управление животными")
public class AnimalController {
    private final AnimalServiceI animalService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить животное по Id")
    public ResponseEntity<Animal> getAnimalById(@PathVariable int id) {
        return animalService.getAnimal(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить животное")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "Создать животное",
            description = "")
    public ResponseEntity<Animal> createAnimal(
            @Valid @RequestBody AnimalRequest request,
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

        var animalSex = Sex.find(request.getSex());
        if (animalSex.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No such animal sex");
        }

        var foodType = FoodType.find(request.getFoodType());
        if (foodType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No such food type");
        }

        var name = new Name(request.getName());

        var ret = animalService.addAnimal(animalType.orElseThrow(), name, animalSex.orElseThrow(), foodType.orElseThrow(), request.getBirthday());

        if (ret.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(ret.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
