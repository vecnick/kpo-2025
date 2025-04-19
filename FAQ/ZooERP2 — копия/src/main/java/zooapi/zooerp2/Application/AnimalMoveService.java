package zooapi.zooerp2.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Events.AnimalMovedEvent;
import zooapi.zooerp2.Domain.Interfaces.Application.AnimalMoveServiceI;
import zooapi.zooerp2.Domain.Interfaces.Application.AnimalServiceI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.EnclosureRepositoryI;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AnimalMoveService implements AnimalMoveServiceI {
    private final AnimalServiceI animalService;
    private final EnclosureRepositoryI enclosureRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void move(int animalId, int enclosureId) throws IllegalArgumentException {
        var curEnclosure = enclosureRepository.getEnclosureByAnimalId(animalId);
        var curEncId = -1;

        Animal animal;
        if (curEnclosure.isPresent()) {
            curEncId = curEnclosure.get().getId();
            animal = curEnclosure.get().removeAnimal(animalId);
        } else {
            var t = animalService.getAnimal(animalId);
            if (t.isPresent()) {
                animal = t.get();
            } else {
                animal = null;
            }
        }

        if (animal == null) {
            throw new IllegalArgumentException("Animal with id " + animalId + " not found");
        }

        var e = enclosureRepository.getEnclosure(enclosureId);

        if (e.isEmpty()) {
            throw new IllegalArgumentException("Enclosure with id " + enclosureId + " not found");
        }

        eventPublisher.publishEvent(new AnimalMovedEvent(animal.getId(), curEncId, e.get().getId(), LocalDateTime.now()));

        e.ifPresent(enclosure -> enclosure.addAnimal(animal));
    }
}
