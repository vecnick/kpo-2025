package erp.domain.events;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IFeedingSchedule;

import java.util.Optional;

import static erp.shared.utils.Date.getLocalDateTimeStr;

public record FeedingTimeEvent(
        int animalId,
        String animalName,
        IAnimalType animalType,
        int animalEnclosureId,
        int feedingScheduleId,
        FoodType foodType,
        String datetime
) {
    public FeedingTimeEvent(IFeedingSchedule feedingSchedule) {
        IAnimal animal = feedingSchedule.getAnimal();
        Optional<IEnclosure> enclosure = animal.getCurrentEnclosure();
        int enclosureId = enclosure.isPresent() ? enclosure.get().getId() : -1;

        this(animal.getId(),
            animal.getName(),
            animal.getAnimalType(),
            enclosureId,
            feedingSchedule.getId(),
            feedingSchedule.getFoodType(),
            getLocalDateTimeStr());
    }

}
