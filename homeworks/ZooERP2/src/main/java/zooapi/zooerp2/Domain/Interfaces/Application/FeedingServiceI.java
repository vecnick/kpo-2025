package zooapi.zooerp2.Domain.Interfaces.Application;

import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Entities.Feeding;
import zooapi.zooerp2.Domain.Entities.FeedingSchedule;
import zooapi.zooerp2.Domain.Enums.FoodType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface FeedingServiceI {
    ArrayList<Feeding> getFeedingSchedule(Date start, Date end);
    void feed(int scheduleId);
    Optional<FeedingSchedule> addFeedingSchedule(int animalId, Date startTime, Duration delta, FoodType foodType);
    void deleteFeedingSchedule(int animalId);
}
