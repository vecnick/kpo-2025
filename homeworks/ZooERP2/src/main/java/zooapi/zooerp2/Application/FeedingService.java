package zooapi.zooerp2.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Feeding;
import zooapi.zooerp2.Domain.Entities.FeedingSchedule;
import zooapi.zooerp2.Domain.Enums.FoodType;
import zooapi.zooerp2.Domain.Factories.FeedingScheduleFactory;
import zooapi.zooerp2.Domain.Interfaces.Application.FeedingServiceI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.FeedingRepositoryI;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeedingService implements FeedingServiceI {
    private final FeedingRepositoryI feedingRepository;
    private final FeedingScheduleFactory feedingScheduleFactory;

    @Override
    public ArrayList<Feeding> getFeedingSchedule(Date start, Date end) {
        if (start.before(end)) {
            return (ArrayList<Feeding>) feedingRepository.getFeedingSchedule(start, end);
        }
        return null;
    }

    @Override
    public void feed(int scheduleId) {
        feedingRepository.feed(scheduleId);
    }

    @Override
    public Optional<FeedingSchedule> addFeedingSchedule(int animalId, Date startTime, Duration delta, FoodType foodType) {
        var newSched = feedingScheduleFactory.createFeedingSchedule(animalId, startTime, delta, foodType);
        return feedingRepository.addFeedingSchedule(newSched);
    }

    @Override
    public void deleteFeedingSchedule(int animalId) {
        feedingRepository.deleteFeedingSchedule(animalId);
    }
}
