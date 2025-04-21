package erp.services;

import erp.domains.Animal;
import erp.interfaces.IAnimalStorage;
import erp.interfaces.IApprover;

import java.util.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.stereotype.Component;

@Component
public class ZooAnimalStorage implements IAnimalStorage {
    private final List<Animal> animals = new ArrayList<>();
    private IApprover approver = null;

    @Override
    public List<Animal> getAnimals() {
        return animals;
    }

    public ZooAnimalStorage(IApprover approver) {
        this.approver = approver;
    }

    @Override
    public boolean addAnimal(Animal animal) {
        if (Objects.nonNull(approver)) {
            return false;
        }
        animals.add(animal);
        return true;
    }

    public  boolean addAnimal(Animal animal, int param) {
        if (Objects.nonNull(approver) && !approver.isApproved(param)) {
            return false;
        }
        animals.add(animal);
        return true;
    }

    @Override
    public Animal takeAnimal(Animal animal) {
        var matchedAnimal = animals.stream()
                .filter(animalInList -> EqualsBuilder.reflectionEquals(animalInList, animal))
                .findFirst();

        matchedAnimal.ifPresent(animals::remove);

        return matchedAnimal.orElse(null);
    }

    @Override
    public Animal takeAnimal(String identificationName) {
        var matchedAnimal = animals.stream()
                .filter(animal -> animal.getIdentificationName().equals(identificationName))
                .findFirst();

        matchedAnimal.ifPresent(animals::remove);

        return matchedAnimal.orElse(null);
    }
}
