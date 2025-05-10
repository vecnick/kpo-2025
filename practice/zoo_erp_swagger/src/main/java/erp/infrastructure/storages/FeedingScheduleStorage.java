package erp.infrastructure.storages;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingSchedule;
import erp.domain.interfaces.IFeedingScheduleFactory;
import erp.infrastructure.interfaces.IFeedingScheduleStorage;
import erp.domain.models.FeedingSchedule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FeedingScheduleStorage implements IFeedingScheduleStorage {
    private final IFeedingScheduleFactory feedingScheduleFactory;
    private List<IFeedingSchedule> feedingSchedules = new ArrayList<>();

    @Override
    public List<IFeedingSchedule> getFeedingSchedules() {
        return feedingSchedules;
    }

    public FeedingScheduleStorage(IFeedingScheduleFactory feedingScheduleFactory) {
        this.feedingScheduleFactory = feedingScheduleFactory;
    }

    @Override
    public Optional<FeedingSchedule> add(IAnimal animal, FoodType foodType, String feedingTime) {
        Optional<FeedingSchedule> feedingSchedule = feedingScheduleFactory.create(animal, foodType, feedingTime);
        if (feedingSchedule.isPresent()) {
            feedingSchedules.add(feedingSchedule.get());
        }
        return feedingSchedule;
    }

    @Override
    public boolean remove(int id) {
        return feedingSchedules.removeIf(schedule -> schedule.getId() == id);
    }
}
