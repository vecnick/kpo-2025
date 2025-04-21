package erp.interfaces;

import erp.domains.Animal;
import erp.enums.Gender;

public interface IAnimalFactory {
    Animal create(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood);
}
