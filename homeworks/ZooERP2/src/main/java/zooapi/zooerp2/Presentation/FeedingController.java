package zooapi.zooerp2.Presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zooapi.zooerp2.Domain.DTO.Requests.AnimalRequest;
import zooapi.zooerp2.Domain.DTO.Requests.FeedingRequest;
import zooapi.zooerp2.Domain.DTO.Requests.NewFeedingRequest;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Entities.Feeding;
import zooapi.zooerp2.Domain.Entities.FeedingSchedule;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.Enums.FoodType;
import zooapi.zooerp2.Domain.Enums.Sex;
import zooapi.zooerp2.Domain.Interfaces.Application.FeedingServiceI;
import zooapi.zooerp2.Domain.ValueObjects.Name;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/feeding")
@RequiredArgsConstructor
@Tag(name = "Приемы пищи", description = "Управление расписанием приемов пищи")
public class FeedingController {
    private final FeedingServiceI feedingService;

    @GetMapping("")
    @Operation(summary = "Получить расписание приемов пищи")
    public ResponseEntity<ArrayList<Feeding>> getFeedingSchedule(@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                 @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        var ret = feedingService.getFeedingSchedule(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Отметить следующее кормление в расписании как выполненное",
            description = "")
    public ResponseEntity<Animal> performFeeding(
            @PathVariable int id) {
        feedingService.feed(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить  расписание")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        feedingService.deleteFeedingSchedule(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "Создать расписание",
            description = "")
    public ResponseEntity<FeedingSchedule> createSchedule(
            @Valid @RequestBody NewFeedingRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }


        var foodType = FoodType.find(request.getFoodType());
        if (foodType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No such food type");
        }


        var ret = feedingService.addFeedingSchedule(request.getAnimalId(), request.getStartTime(), request.getDelta(), foodType.orElseThrow());

        if (ret.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(ret.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
