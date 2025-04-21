package erp.factories;

import erp.domains.FeedingSchedule;
import erp.enums.FoodType;
import erp.interfaces.IFeedingScheduleFactory;

import static erp.utils.Time.isTimeFormat;

public class FeedingScheduleFactory implements IFeedingScheduleFactory {

    @Override
    public FeedingSchedule create(FoodType foodType, String feedingTime) {
        if (!isTimeFormat(feedingTime)) {
            throw new IllegalArgumentException("Время не соответствует формату");
        }
        return new FeedingSchedule(foodType, feedingTime);
    }
}
