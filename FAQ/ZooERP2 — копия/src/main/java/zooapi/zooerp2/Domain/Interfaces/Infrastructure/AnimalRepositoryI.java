package zooapi.zooerp2.Domain.Interfaces.Infrastructure;

import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.Enums.FoodType;
import zooapi.zooerp2.Domain.Enums.Sex;
import zooapi.zooerp2.Domain.ValueObjects.Name;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface AnimalRepositoryI {
    Optional<Animal> addAnimal(Animal animal);
    void deleteAnimal(int animalId);
    Optional<Animal> getAnimal(int animalId);
    int getAnimalCount();
}
