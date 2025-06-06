package erp.domain.events;

import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;

import static erp.shared.utils.Date.getLocalDateTimeStr;

public record AnimalMovedEvent(
        int animalId,
        String animalName,
        IAnimalType animalType,
        int enclosureToId,
        int enclosureFromId,
        String datetime
) {
    public AnimalMovedEvent(IAnimal animal, IEnclosure enclosureFrom, IEnclosure enclosureTo) {
        this(animal.getId(),
            animal.getName(),
            animal.getAnimalType(),
            enclosureFrom.getId(),
            enclosureTo.getId(),
            getLocalDateTimeStr());
    }
}
