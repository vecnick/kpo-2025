package erp.domain.interfaces;

import erp.domain.models.Animal;
import erp.domain.enums.Gender;

import java.util.Optional;

public interface IAnimalFactory {
    Optional<Animal> create(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood);
}
