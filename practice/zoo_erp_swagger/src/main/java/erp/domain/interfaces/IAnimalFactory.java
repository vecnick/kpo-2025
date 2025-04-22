package erp.domain.interfaces;

import erp.domain.models.Animal;
import erp.domain.enums.Gender;

public interface IAnimalFactory {
    Animal create(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood);
}
