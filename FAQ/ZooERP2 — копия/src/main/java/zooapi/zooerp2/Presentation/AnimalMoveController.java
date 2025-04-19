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
import zooapi.zooerp2.Domain.DTO.Requests.AnimalMoveRequest;
import zooapi.zooerp2.Domain.Entities.FeedingSchedule;
import zooapi.zooerp2.Domain.Interfaces.Application.AnimalMoveServiceI;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/animal-move")
@RequiredArgsConstructor
@Tag(name = "Перемещение животных между вольерами", description = "Управление перемещением животных")
public class AnimalMoveController {
    private final AnimalMoveServiceI animalMoveService;

    @PostMapping("/")
    @Operation(summary = "Перевести животное с заданным id в вольер с заданным id")
    public ResponseEntity<Boolean> moveAnimal(@Valid @RequestBody AnimalMoveRequest request,
                                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        try {
            animalMoveService.move(request.getAnimalId(), request.getEnclosureId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user or such closure");
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}