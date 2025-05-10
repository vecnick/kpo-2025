package erp.domain.factories;

import erp.domain.models.FeedingSchedule;
import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingScheduleFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static erp.shared.utils.Time.isTimeFormat;

@Component
public class FeedingScheduleFactory implements IFeedingScheduleFactory {
    private int lastId = 0;

    @Override
    public Optional<FeedingSchedule> create(IAnimal animal, FoodType foodType, String feedingTime) {
        if (!isTimeFormat(feedingTime)) {
//            throw new IllegalArgumentException("Время не соответствует формату");
            return Optional.empty();
        }
        return Optional.of(new FeedingSchedule(lastId++, animal, foodType, feedingTime));
    }
}
