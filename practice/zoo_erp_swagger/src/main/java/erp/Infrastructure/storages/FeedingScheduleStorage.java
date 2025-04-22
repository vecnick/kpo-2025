package erp.Infrastructure.storages;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingSchedule;
import erp.domain.interfaces.IFeedingScheduleFactory;
import erp.Infrastructure.interfaces.IFeedingScheduleStorage;

import java.util.List;

public class FeedingScheduleStorage implements IFeedingScheduleStorage {
    private final IFeedingScheduleFactory feedingScheduleFactory;
    private List<IFeedingSchedule> feedingSchedules;

    @Override
    public List<IFeedingSchedule> getFeedingSchedules() {
        return feedingSchedules;
    }

    public FeedingScheduleStorage(IFeedingScheduleFactory feedingScheduleFactory) {
        this.feedingScheduleFactory = feedingScheduleFactory;
    }

    @Override
    public void add(IAnimal animal, FoodType foodType, String feedingTime) {
        IFeedingSchedule feedingSchedule = feedingScheduleFactory.create(animal, foodType, feedingTime);
        feedingSchedules.add(feedingSchedule);
    }

    @Override
    public void remove(int id) {
        feedingSchedules.stream().filter(e -> e.getId() == id)
                .findFirst().ifPresent(feedingSchedules::remove);
    }
}
