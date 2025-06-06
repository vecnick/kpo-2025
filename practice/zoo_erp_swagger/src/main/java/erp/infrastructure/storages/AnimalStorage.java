package erp.infrastructure.storages;

import erp.infrastructure.interfaces.IAnimalStorage;
import erp.domain.enums.Gender;
import erp.domain.interfaces.*;
import erp.domain.models.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimalStorage implements IAnimalStorage {
    private final IAnimalFactory animalFactory;
    private List<IAnimal> animals = new ArrayList<>();

    @Override
    public List<IAnimal> getAnimals() {
        return animals;
    }

    public AnimalStorage(IAnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
    }

    @Override
    public Optional<Animal> add(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood) {
        Optional<Animal> animal = animalFactory.create(animalType, name, date, gender, favoriteFood);
        if (animal.isPresent()) {
            animals.add(animal.get());
        }
        return animal;
    }

    @Override
    public boolean remove(int id) {
        return animals.removeIf(animal -> animal.getId() == id);
    }
}
