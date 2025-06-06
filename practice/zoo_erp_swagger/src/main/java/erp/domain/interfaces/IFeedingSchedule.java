package erp.domain.interfaces;

import erp.domain.enums.FoodType;

import java.util.List;

public interface IFeedingSchedule {

    IAnimal getAnimal();
    String getFeedingTime();
    FoodType getFoodType();
    int getId();
    List<String> getCompletedDates();

    void setSchedule(String time);
    void markCompleted(String date);
}
