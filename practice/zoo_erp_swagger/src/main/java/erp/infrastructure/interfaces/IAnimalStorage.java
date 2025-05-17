package erp.infrastructure.interfaces;

import erp.domain.enums.Gender;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.models.Animal;

import java.util.List;
import java.util.Optional;

public interface IAnimalStorage {

    List<IAnimal> getAnimals();
    Optional<Animal> add(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood);
    boolean remove(int id);
}
