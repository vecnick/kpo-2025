package erp.domain.interfaces;

import erp.domain.models.FeedingSchedule;
import erp.domain.enums.FoodType;

import java.util.Optional;

public interface IFeedingScheduleFactory {
    Optional<FeedingSchedule> create(IAnimal animal, FoodType foodType, String feedingTime);
}
