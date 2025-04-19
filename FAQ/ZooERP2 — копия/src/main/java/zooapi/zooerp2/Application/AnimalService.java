package zooapi.zooerp2.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.Enums.FoodType;
import zooapi.zooerp2.Domain.Enums.Sex;
import zooapi.zooerp2.Domain.Factories.AnimalFactory;
import zooapi.zooerp2.Domain.Interfaces.Application.AnimalServiceI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.AnimalRepositoryI;
import zooapi.zooerp2.Domain.ValueObjects.Name;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnimalService implements AnimalServiceI {
    private final AnimalRepositoryI animalRepository;
    private final AnimalFactory animalFactory;


    @Override
    public Optional<Animal> addAnimal(AnimalType animalType, Name name, Sex sex, FoodType foodType, Date birthday) {
        var newAnimal = animalFactory.createAnimal(animalType, name, sex, foodType, birthday);
        return animalRepository.addAnimal(newAnimal);
    }

    @Override
    public void deleteAnimal(int animalId) {
        animalRepository.deleteAnimal(animalId);
    }

    @Override
    public Optional<Animal> getAnimal(int animalId) {
        return animalRepository.getAnimal(animalId);
    }
}
