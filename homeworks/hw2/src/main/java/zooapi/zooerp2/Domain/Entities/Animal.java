package zooapi.zooerp2.Domain.Entities;

import lombok.Getter;
import zooapi.zooerp2.Domain.Enums.*;
import zooapi.zooerp2.Domain.ValueObjects.Name;

import java.util.Date;
import java.util.UUID;

@Getter
public class Animal {
    private int id;
    private AnimalType animalType;
    private Name name;
    private Date birthday;
    private Sex sex;
    private FoodType foodType;
    private Status status;
    private FoodStatus foodStatus;

    public void feed(){
        this.foodStatus = FoodStatus.FULL;
    }

    public void Treat() {
        this.status = Status.HEALTHY;
    }

    //TODO: Implement
    public void moveToEnclosure(Enclosure enclosure) {}

    public Animal(int id, AnimalType animalType, Name name, Sex sex, FoodType foodType, Date birthday) {
        this.id = id;
        this.animalType = animalType;
        this.name = name;
        this.sex = sex;
        this.foodType = foodType;
        this.birthday = birthday;
        this.status = Status.HEALTHY;
        this.foodStatus = FoodStatus.FULL;
    }
}
