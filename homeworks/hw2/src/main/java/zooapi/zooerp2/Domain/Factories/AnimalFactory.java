package zooapi.zooerp2.Domain.Factories;

import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Enums.*;
import zooapi.zooerp2.Domain.ValueObjects.Name;

import java.util.Date;
import java.util.UUID;

@Component
public class AnimalFactory {
    int lastId = 0;

    public Animal createAnimal(AnimalType animalType, Name name, Sex sex, FoodType foodType, Date birthday) {
        return new Animal(++lastId, animalType, name, sex, foodType, birthday);
    }
}
