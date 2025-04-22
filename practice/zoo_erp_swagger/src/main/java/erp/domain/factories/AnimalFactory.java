package erp.domain.factories;

import erp.domain.models.Animal;
import erp.domain.enums.Gender;
import erp.domain.interfaces.IAnimalFactory;
import erp.domain.interfaces.IAnimalType;

import static erp.shared.utils.Date.isDateFormat;

public class AnimalFactory implements IAnimalFactory {
    private int lastId = 0;

    @Override
    public Animal create(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood) {
        if (!isDateFormat(date)) {
            throw new IllegalArgumentException("Дата не соответствует формату");
        }
        return new Animal(lastId++, animalType, name, date, gender, favoriteFood);
    }
}
