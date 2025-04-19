package zooapi.zooerp2.Domain.Entities;

import lombok.Getter;
import zooapi.zooerp2.Domain.Enums.FoodType;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Getter
public class Feeding {
    private int animalId;
    private Date startTime;
    private FoodType foodType;

    public Feeding(int animalId, Date startTime, FoodType foodType) {
        this.animalId = animalId;
        this.startTime = startTime;
        this.foodType = foodType;
    }
}
