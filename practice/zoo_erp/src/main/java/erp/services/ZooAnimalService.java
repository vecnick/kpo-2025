package erp.services;

import erp.domains.Animal;
import erp.interfaces.IAnimalStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZooAnimalService {
    IAnimalStorage animalStorage;

    public ZooAnimalService(IAnimalStorage animalStorage) {
        this.animalStorage = animalStorage;
    }

    public int getTotalFood() {
        return animalStorage.getAnimals().stream()
                .mapToInt(Animal::getFood).sum();
    }

    public List<Animal> getInteractiveAnimals() {
        return animalStorage.getAnimals().stream()
                .filter(Animal::isInteractiveAllowed)
                .collect(Collectors.toList());
    }

    public int getAnimalsNumber() {
        return animalStorage.getAnimals().size();
    }

    public List<String> getAnimalsIdentificationName() {
        return animalStorage.getAnimals().stream()
                .map(Animal::getIdentificationName)
                .collect(Collectors.toList());
    }
}
