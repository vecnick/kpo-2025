package zooapi.zooerp2.Domain.Entities;

import lombok.Getter;
import zooapi.zooerp2.Domain.Enums.FoodType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class FeedingSchedule {
    private int id;
    private int animalId;
    private Date startTime;
    private Date nextTime;
    private Duration delta;
    private FoodType foodType;

    public FeedingSchedule(int id, int animalId, Date startTime, Duration delta, FoodType foodType) {
        this.id = id;
        this.animalId = animalId;
        this.startTime = startTime;
        this.delta = delta;
        this.foodType = foodType;
        this.nextTime = startTime;
    }

    public void completeFeeding() {
        nextTime = Date.from(nextTime.toInstant().plus(delta));
    }

    public void changeDelta(Duration delta) {
        this.delta = delta;
    }

    public List<Feeding> getFeedingsInPeriod(Date start, Date end) {
        ArrayList<Feeding> feedings = new ArrayList<>();

        var current = this.startTime;

        while (current.before(end)) {
            if (current.after(start)) {
                feedings.add(new Feeding(this.animalId, current, this.foodType));
            }

            current = Date.from(current.toInstant().plus(this.delta));
        }

        return feedings;
    }
}
