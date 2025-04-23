package erp.presentation.controllers;

import erp.infrastructure.interfaces.IAnimalStorage;
import erp.infrastructure.interfaces.IEnclosureStorage;
import erp.infrastructure.interfaces.IFeedingScheduleStorage;
import erp.application.interfaces.IAnimalTransferService;
import erp.application.interfaces.IZooStatisticsService;
import erp.domain.enums.FoodType;
import erp.domain.enums.Gender;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import erp.domain.models.Animal;
import erp.domain.models.animalType.Herbo;
import erp.domain.models.animalType.Predator;
import erp.presentation.responses.AnimalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnimalController {
    private final IAnimalStorage animalStorage;
    private final IEnclosureStorage enclosureStorage;
    private final IFeedingScheduleStorage feedingScheduleStorage;
    private final IAnimalTransferService animalTransferService;
    private final IZooStatisticsService zooStatisticsService;

    public AnimalController(IAnimalStorage animalStorage, IEnclosureStorage enclosureStorage, IFeedingScheduleStorage feedingScheduleStorage, IAnimalTransferService animalTransferService, IZooStatisticsService zooStatisticsService) {
        this.animalStorage = animalStorage;
        this.enclosureStorage = enclosureStorage;
        this.feedingScheduleStorage = feedingScheduleStorage;
        this.animalTransferService = animalTransferService;
        this.zooStatisticsService = zooStatisticsService;
    }

    private Optional<IAnimal> getAnimalById(int id) {
        return animalStorage.getAnimals().stream()
                .filter(animal -> animal.getId() == id).findFirst();
    }

    @GetMapping("/animals")
    public ResponseEntity<List<AnimalResponse>> getAllAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/haveEnclosure")
    public ResponseEntity<List<AnimalResponse>> getEnclosuresAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getEnclosuresAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/haveNotEnclosure")
    public ResponseEntity<List<AnimalResponse>> getNonEnclosuresAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getNonEnclosuresAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/haveSchedule")
    public ResponseEntity<List<AnimalResponse>> getSchedulesAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getSchedulesAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/haveNotSchedule")
    public ResponseEntity<List<AnimalResponse>> getNonSchedulesAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getNonSchedulesAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/sick")
    public ResponseEntity<List<AnimalResponse>> getSickAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getSickAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/hungry")
    public ResponseEntity<List<AnimalResponse>> getHungryAnimals() {
        return ResponseEntity.ok(zooStatisticsService.getHungryAnimals().stream()
                .map(AnimalResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<AnimalResponse> getAnimal(@PathVariable int id) {
        return getAnimalById(id).map(animal -> ResponseEntity.ok(new AnimalResponse(animal)))
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/animals/{id}/makeSick")
    public ResponseEntity<Void> makeSick(@PathVariable int id) {
        return getAnimalById(id).<ResponseEntity<Void>>map(animal -> {
            animal.sick();
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/animals/{id}/makeHungry")
    public ResponseEntity<Void> makeHungry(@PathVariable int id) {
        return getAnimalById(id).<ResponseEntity<Void>>map(animal -> {
            animal.hungry();
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/animals/{id}/heal")
    public ResponseEntity<Void> heal(@PathVariable int id) {
        return getAnimalById(id).<ResponseEntity<Void>>map(animal -> {
            animal.heal();
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/animals/{id}/feed")
    public ResponseEntity<Void> feed(@PathVariable int id) {
        return getAnimalById(id).<ResponseEntity<Void>>map(animal -> {
            animal.feed();
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/animals/{animalId}/enclosure/{enclosureId}/moveToEnclosure")
    public ResponseEntity<Void> moveToEnclosure(@PathVariable int animalId, int enclosureId) {
        Optional<IEnclosure> encolsure = enclosureStorage.getEnclosures().stream()
                .filter(enclosure -> enclosure.getId() == enclosureId).findFirst();
        Optional<IAnimal> animal = getAnimalById(animalId);

        if (encolsure.isEmpty() || animal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        animalTransferService.transfer(encolsure.get(), animal.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/animals")
    public ResponseEntity<AnimalResponse> createAnimal(FoodType foodType, int kindness, String name, String date, Gender gender, String favoriteFood) {
        IAnimalType animalType = (foodType == FoodType.MEAT) ? new Predator() : new Herbo(kindness);
        Optional<Animal> animal = animalStorage.add(animalType, name, date, gender, favoriteFood);

        if (animal.isPresent()) {
            return ResponseEntity.ok(new AnimalResponse(animal.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/animals/{id}")
    public ResponseEntity<Void> deleteAnimal(int id) {
        // Удаляем животное из вольера (если оно там есть)
        enclosureStorage.getEnclosures().stream().forEach(enclosure -> enclosure.remove(id));

        // Удаляем расписание для животного (если оно там есть)
        feedingScheduleStorage.getFeedingSchedules()
                .removeIf(schedule -> schedule.getAnimal().getId() == id);

        if (animalStorage.remove(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
