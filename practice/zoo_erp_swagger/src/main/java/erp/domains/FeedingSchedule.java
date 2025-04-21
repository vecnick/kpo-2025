package erp.domains;

import erp.enums.FoodType;
import erp.interfaces.ISchedule;

import java.util.List;

public class FeedingSchedule implements ISchedule {
    private String feedingTime;
    private final FoodType foodType;
    private List<String> compleetedDates;

    public FeedingSchedule(FoodType foodType, String feedingTime) {
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
