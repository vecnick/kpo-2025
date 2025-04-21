package erp.factories;

import erp.domains.FeedingSchedule;
import erp.enums.FoodType;
import erp.interfaces.IAlive;
import erp.interfaces.IFeedingScheduleFactory;

import static erp.utils.Time.isTimeFormat;

public class FeedingScheduleFactory implements IFeedingScheduleFactory {
    private int lastId = 0;

    @Override
    public FeedingSchedule create(IAlive animal, FoodType foodType, String feedingTime) {
        if (!isTimeFormat(feedingTime)) {
            throw new IllegalArgumentException("Время не соответствует формату");
        }
        return new FeedingSchedule(lastId++, animal, foodType, feedingTime);
    }
}
