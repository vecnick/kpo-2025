package erp.domain.factories;

import erp.domain.models.FeedingSchedule;
import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingScheduleFactory;

import static erp.shared.utils.Time.isTimeFormat;

public class FeedingScheduleFactory implements IFeedingScheduleFactory {
    private int lastId = 0;

    @Override
    public FeedingSchedule create(IAnimal animal, FoodType foodType, String feedingTime) {
        if (!isTimeFormat(feedingTime)) {
            throw new IllegalArgumentException("Время не соответствует формату");
        }
        return new FeedingSchedule(lastId++, animal, foodType, feedingTime);
    }
}
