package zooapi.zooerp2.Infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Feeding;
import zooapi.zooerp2.Domain.Entities.FeedingSchedule;
import zooapi.zooerp2.Domain.Enums.FoodType;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.FeedingRepositoryI;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FeedingRepository implements FeedingRepositoryI {
    ArrayList<FeedingSchedule> feedingSchedules = new ArrayList<>();

    @Override
    public List<Feeding> getFeedingSchedule(Date start, Date end) {
        ArrayList<Feeding> feedings = new ArrayList<>();

        for (var schedule: feedingSchedules) {
            feedings.addAll(schedule.getFeedingsInPeriod(start, end));
        }

        return feedings;
    }

    @Override
    public void feed(int scheduleId) {
        var t = feedingSchedules.stream().filter(schedule -> schedule.getId() == scheduleId).findFirst();
        t.ifPresent(FeedingSchedule::completeFeeding);
    }

    @Override
    public Optional<FeedingSchedule> addFeedingSchedule(FeedingSchedule feedingSchedule) {
        if (!feedingSchedules.contains(feedingSchedule)) {
            feedingSchedules.add(feedingSchedule);
            return Optional.of(feedingSchedule);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteFeedingSchedule(int scheduleId) {
        var t = feedingSchedules.stream().filter(schedule -> schedule.getId() == scheduleId).findFirst();
        t.ifPresent(schedule -> feedingSchedules.remove(schedule));
    }

    @Override
    public int getFeedingScheduleCount() {
        return feedingSchedules.size();
    }
}
