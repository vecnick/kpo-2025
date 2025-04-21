package erp.domains;

import erp.enums.FoodType;
import erp.interfaces.IAlive;
import erp.interfaces.ISchedule;

import java.util.List;

public class FeedingSchedule implements ISchedule {
    private final IAlive animal;
    private String feedingTime;
    private final FoodType foodType;

    private final int id;
    private List<String> compleetedDates;

    @Override
    public int getId() {
        return id;
    }

    public FeedingSchedule(int id, IAlive animal, FoodType foodType, String feedingTime) {
        this.id = id;
        this.animal = animal;
        this.foodType = foodType;
        this.feedingTime = feedingTime;
    }

    @Override
    public void setSchedule(String time) {
        feedingTime = time;
    }

    @Override
    public void markCompleted(String date) {
        compleetedDates.add(date);
    }
}
