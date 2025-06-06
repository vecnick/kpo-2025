package erp.presentation.controllers;

import erp.infrastructure.interfaces.IAnimalStorage;
import erp.infrastructure.interfaces.IEnclosureStorage;
import erp.application.interfaces.IAnimalTransferService;
import erp.application.interfaces.IZooStatisticsService;
import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import erp.domain.models.Enclosure;
import erp.domain.models.animalType.Herbo;
import erp.domain.models.animalType.Predator;
import erp.presentation.responses.EnclosureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EnclosureController {
    private final IEnclosureStorage enclosureStorage;
    private final IAnimalStorage animalStorage;
    private final IZooStatisticsService zooStatisticsService;
    private final IAnimalTransferService animalTransferService;

    public EnclosureController(IEnclosureStorage enclosureStorage, IAnimalStorage animalStorage, IZooStatisticsService zooStatisticsService, IAnimalTransferService animalTransferService) {
        this.enclosureStorage = enclosureStorage;
        this.animalStorage = animalStorage;
        this.zooStatisticsService = zooStatisticsService;
        this.animalTransferService = animalTransferService;
    }

    private Optional<IEnclosure> getEnclosureById(int id) {
        return enclosureStorage.getEnclosures().stream()
                .filter(enclosure -> enclosure.getId() == id).findFirst();
    }

    @GetMapping("/enclosures")
    public ResponseEntity<List<EnclosureResponse>> getAllEnclosures() {
        return ResponseEntity.ok(zooStatisticsService.getEnclosures().stream()
                .map(EnclosureResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/enclosures/{id}")
    public ResponseEntity<EnclosureResponse> getEnclosure(@PathVariable int id) {
        return getEnclosureById(id).map(enclosure -> ResponseEntity.ok(new EnclosureResponse(enclosure)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/enclosures/{id}/makeDirty")
    public ResponseEntity<Void> makeDirty(@PathVariable int id) {
        return getEnclosureById(id).<ResponseEntity<Void>>map(enclosure -> {
            enclosure.dirty();
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/enclosures/{id}/clean")
    public ResponseEntity<Void> clean(@PathVariable int id) {
        return getEnclosureById(id).<ResponseEntity<Void>>map(enclosure -> {
            enclosure.clean();
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/enclosures/{enclosureId}/animal/{animalId}/addAnimal")
    public ResponseEntity<Void> addAnimal(@PathVariable int enclosureId, int animalId) {
        Optional<IEnclosure> enclosure = getEnclosureById(enclosureId);
        Optional<IAnimal> animal = animalStorage.getAnimals().stream().filter(a -> a.getId() == animalId).findFirst();
        if (enclosure.isEmpty() || animal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (animalTransferService.transfer(enclosure.get(), animal.get())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/enclosures/{enclosureId}/animal/{animalId}/removeAnimal")
    public ResponseEntity<Void> removeAnimal(@PathVariable int enclosureId, int animalId) {
        Optional<IEnclosure> enclosure = getEnclosureById(enclosureId);

        if (enclosure.isEmpty() || !enclosure.get().remove(animalId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/enclosures")
    public ResponseEntity<EnclosureResponse> createEnclosure(FoodType foodType, int kindness, int size, int maxAnimalsCount) {
        IAnimalType animalType = (foodType == FoodType.MEAT) ? new Predator() : new Herbo(kindness);
        Optional<Enclosure> enclosure = enclosureStorage.add(animalType, size, maxAnimalsCount);

        if (enclosure.isPresent()) {
            return ResponseEntity.ok(new EnclosureResponse(enclosure.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/enclosures/{id}")
    public ResponseEntity<Void> deleteEnclosure(int id) {

        Optional<IEnclosure> enclosure = getEnclosureById(id);
        if (enclosure.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Удаляем связь для животных в этом вольере
        enclosure.get().getAnimals().forEach(IAnimal::clearCurrentEnclosure);

        // Удаляем вольер
        enclosureStorage.remove(id);

        return ResponseEntity.noContent().build();
    }
}
