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
        this(feedingSchedule.getAnimal().getId(),
            feedingSchedule.getAnimal().getName(),
            feedingSchedule.getAnimal().getAnimalType(),
            feedingSchedule.getAnimal().getCurrentEnclosure().isPresent() ? feedingSchedule.getAnimal().getCurrentEnclosure().get().getId() : -1,
            feedingSchedule.getId(),
            feedingSchedule.getFoodType(),
            getLocalDateTimeStr());
    }

}
