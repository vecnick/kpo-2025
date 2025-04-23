package erp.domain.factories;

import erp.domain.interfaces.IAnimal;
import erp.domain.models.Animal;
import erp.domain.enums.Gender;
import erp.domain.interfaces.IAnimalFactory;
import erp.domain.interfaces.IAnimalType;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static erp.shared.utils.Date.isDateFormat;

@Component
public class AnimalFactory implements IAnimalFactory {
    private int lastId = 0;

    @Override
    public Optional<Animal> create(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood) {
        if (!isDateFormat(date)) {
//            throw new IllegalArgumentException("Дата не соответствует формату");
            return Optional.empty();
        }
        return Optional.of(new Animal(lastId++, animalType, name, date, gender, favoriteFood));
    }
}
