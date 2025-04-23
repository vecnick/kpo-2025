package erp.domain.models;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedingSchedule implements IFeedingSchedule {
    private final IAnimal animal;
    private String feedingTime;
    private final FoodType foodType;

    private final int id;
    private List<String> completedDates = new ArrayList<>();

    @Override
    public IAnimal getAnimal() { return animal; }

    @Override
    public String getFeedingTime() { return feedingTime; }

    @Override
    public FoodType getFoodType() { return foodType; }

    @Override
    public int getId() { return id; }

    @Override
    public List<String> getCompletedDates() { return completedDates; }

    public FeedingSchedule(int id, IAnimal animal, FoodType foodType, String feedingTime) {
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
        completedDates.add(date);
    }
}
