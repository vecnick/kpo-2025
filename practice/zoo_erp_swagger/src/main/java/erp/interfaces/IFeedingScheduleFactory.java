package erp.interfaces;

import erp.domains.FeedingSchedule;
import erp.enums.FoodType;

public interface IFeedingScheduleFactory {
    FeedingSchedule create(IAlive animal, FoodType foodType, String feedingTime);
}
