package erp.infrastructure.interfaces;

import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingSchedule;
import erp.domain.enums.FoodType;
import erp.domain.models.FeedingSchedule;

import java.util.List;
import java.util.Optional;

public interface IFeedingScheduleStorage {

    List<IFeedingSchedule> getFeedingSchedules();
    Optional<FeedingSchedule> add(IAnimal animal, FoodType foodType, String feedingTime);
    boolean remove(int id);
}
