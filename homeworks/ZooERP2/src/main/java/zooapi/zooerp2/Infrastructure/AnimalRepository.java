package zooapi.zooerp2.Infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.AnimalRepositoryI;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnimalRepository implements AnimalRepositoryI {
    ArrayList<Animal> animals = new ArrayList<>();

    @Override
    public Optional<Animal> addAnimal(Animal animal) {
        if (!animals.contains(animal)) {
            animals.add(animal);
            return Optional.of(animal);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAnimal(int animalId) {
        var t = animals.stream().filter(animal -> animal.getId() == animalId).findFirst();
        t.ifPresent(animal -> animals.remove(animal));
    }


    @Override
    public Optional<Animal> getAnimal(int animalId) {
        return animals.stream().filter(animal -> animal.getId() == animalId).findFirst();
    }

    @Override
    public int getAnimalCount() {
        return animals.size();
    }
}
