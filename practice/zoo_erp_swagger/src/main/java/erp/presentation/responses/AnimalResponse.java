package erp.presentation.responses;

import erp.domain.enums.Gender;
import erp.domain.enums.Health;
import erp.domain.enums.Hunger;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;

import java.util.Optional;

public record AnimalResponse(
    int id,
    AnimalTypeResponse animalTypeResponse,
    String name,
    String date,
    Gender gender,
    String favoriteFood,
    int currentEnclosureId,
    Health health,
    Hunger hunger
) {
    public AnimalResponse(IAnimal animal) {
        this(animal.getId(),
            new AnimalTypeResponse(animal.getAnimalType()),
            animal.getName(),
            animal.getDate(),
            animal.getGender(),
            animal.getFavoriteFood(),
            animal.getCurrentEnclosure().isPresent() ? animal.getCurrentEnclosure().get().getId() : -1,
            animal.getHealth(),
            animal.getHunger());
    }
}
