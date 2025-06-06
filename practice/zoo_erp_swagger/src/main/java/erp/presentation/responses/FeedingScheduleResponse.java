package erp.presentation.responses;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IFeedingSchedule;

import java.util.List;

public record FeedingScheduleResponse(
        int id,
        int animalId,
        String feedingTime,
        FoodType foodType,
        List<String> completedDates
) {
    public FeedingScheduleResponse(IFeedingSchedule feedingSchedule) {
        this(feedingSchedule.getId(),
            feedingSchedule.getAnimal().getId(),
            feedingSchedule.getFeedingTime(),
            feedingSchedule.getFoodType(),
            feedingSchedule.getCompletedDates());
    }
}