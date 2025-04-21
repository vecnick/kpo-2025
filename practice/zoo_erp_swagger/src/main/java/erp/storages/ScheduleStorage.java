package erp.storages;

import erp.domains.FeedingSchedule;
import erp.enums.FoodType;
import erp.factories.FeedingScheduleFactory;
import erp.interfaces.IAlive;
import erp.interfaces.IAnimalType;
import erp.interfaces.IEnclosure;
import erp.interfaces.ISchedule;

import java.util.List;

public class ScheduleStorage {
    private final FeedingScheduleFactory scheduleFactory;
    private List<ISchedule> schedules;

    public List<ISchedule> getSchedules() {
        return schedules;
    }

    public ScheduleStorage(FeedingScheduleFactory scheduleFactory) {
        this.scheduleFactory = scheduleFactory;
    }

    public void add(IAlive animal, FoodType foodType, String feedingTime) {
        ISchedule schedule = scheduleFactory.create(animal, foodType, feedingTime);
        schedules.add(schedule);
    }

    public void remove(int id) {
        schedules.stream().filter(e -> e.getId() == id)
                .findFirst().ifPresent(schedules::remove);
    }
}
