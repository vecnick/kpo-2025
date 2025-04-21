package erp.interfaces;

import erp.domains.FeedingSchedule;
import erp.enums.FoodType;

public interface IFeedingScheduleFactory {
    FeedingSchedule create(FoodType foodType, String feedingTime);
}
