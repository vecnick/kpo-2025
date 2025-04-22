package erp.Infrastructure.interfaces;

import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingSchedule;
import erp.domain.enums.FoodType;

import java.util.List;

public interface IFeedingScheduleStorage {

    List<IFeedingSchedule> getFeedingSchedules();
    void add(IAnimal animal, FoodType foodType, String feedingTime);
    void remove(int id);
}
