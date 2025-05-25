package erp.presentation.controllers;

import erp.infrastructure.interfaces.IAnimalStorage;
import erp.infrastructure.interfaces.IFeedingScheduleStorage;
import erp.application.interfaces.IFeedingOrganizationService;
import erp.application.interfaces.IZooStatisticsService;
import erp.domain.enums.FoodType;
import erp.domain.interfaces.*;
import erp.domain.models.FeedingSchedule;
import erp.presentation.responses.FeedingScheduleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FeedingScheduleController {
    private final IFeedingScheduleStorage feedingScheduleStorage;
    private final IFeedingOrganizationService feedingOrganizationService;
    private final IZooStatisticsService zooStatisticsService;
    private final IAnimalStorage animalStorage;

    public FeedingScheduleController(IFeedingScheduleStorage feedingScheduleStorage, IFeedingOrganizationService feedingOrganizationService, IZooStatisticsService zooStatisticsService, IAnimalStorage animalStorage) {
        this.feedingScheduleStorage = feedingScheduleStorage;
        this.feedingOrganizationService = feedingOrganizationService;
        this.zooStatisticsService = zooStatisticsService;
        this.animalStorage = animalStorage;
    }

    private Optional<IFeedingSchedule> getFeedingScheduleById(int id) {
        return feedingScheduleStorage.getFeedingSchedules().stream()
                .filter(schedule -> schedule.getId() == id).findFirst();
    }

    @GetMapping("/feedingSchedule")
    public ResponseEntity<List<FeedingScheduleResponse>> getAllFeedingSchedules() {
        return ResponseEntity.ok(zooStatisticsService.getFeedingSchedules().stream()
                .map(FeedingScheduleResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/feedingSchedule/{id}")
    public ResponseEntity<FeedingScheduleResponse> getFeedingSchedule(@PathVariable int id) {
        return getFeedingScheduleById(id).map(schedule -> ResponseEntity.ok(new FeedingScheduleResponse(schedule)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/feedingSchedule/{id}/feed")
    public ResponseEntity<Void> feed(@PathVariable int id) {
        Optional<IFeedingSchedule> feedingSchedule = getFeedingScheduleById(id);
        if (feedingSchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        feedingOrganizationService.feedAnimalBySchedule(feedingSchedule.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/feedingSchedule/feedAll")
    public ResponseEntity<Void> feedAll() {
        feedingOrganizationService.feedAllANimalsBySchedule();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/feedingSchedule")
    public ResponseEntity<FeedingScheduleResponse> createEnclosure(int animalId, FoodType foodType, String feedingTime) {
        Optional<IAnimal> animal = animalStorage.getAnimals().stream().filter(a -> a.getId() == animalId).findFirst();
        if (animal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<FeedingSchedule> feedingSchedule = feedingScheduleStorage.add(animal.get(), foodType, feedingTime);
        if (feedingSchedule.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new FeedingScheduleResponse(feedingSchedule.get()));
    }

    @DeleteMapping("/feedingSchedule/{id}")
    public ResponseEntity<Void> deleteEnclosure(int id) {

        Optional<IFeedingSchedule> feedingSchedule = getFeedingScheduleById(id);
        if (feedingSchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        feedingScheduleStorage.remove(id);
        return ResponseEntity.noContent().build();
    }
}
