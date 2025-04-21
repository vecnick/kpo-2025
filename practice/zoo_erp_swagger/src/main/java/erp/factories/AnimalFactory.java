package erp.factories;

import erp.domains.Animal;
import erp.enums.Gender;
import erp.interfaces.IAnimalFactory;
import erp.interfaces.IAnimalType;

import static erp.utils.Date.isDateFormat;

public class AnimalFactory implements IAnimalFactory {

    @Override
    public Animal create(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood) {
        if (!isDateFormat(date)) {
            throw new IllegalArgumentException("Дата не соответствует формату");
        }
        return new Animal(animalType, name, date, gender, favoriteFood);
    }
}
