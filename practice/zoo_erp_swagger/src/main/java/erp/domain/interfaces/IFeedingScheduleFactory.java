package erp.domain.interfaces;

import erp.domain.models.FeedingSchedule;
import erp.domain.enums.FoodType;

public interface IFeedingScheduleFactory {
    FeedingSchedule create(IAnimal animal, FoodType foodType, String feedingTime);
}
