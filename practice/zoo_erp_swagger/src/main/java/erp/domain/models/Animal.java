package erp.domain.models;

import erp.domain.enums.Gender;
import erp.domain.enums.Health;
import erp.domain.enums.Hunger;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import lombok.ToString;

import java.util.Optional;

@ToString
public class Animal implements IAnimal {
    private final IAnimalType animalType;
    private final String name;
    private final String date;
    private final Gender gender;
    private String favoriteFood;

    private final int id;
    private Optional<IEnclosure> currentEnclosure = Optional.empty();
    private Health health = Health.HEALTHY;
    private Hunger hunger= Hunger.FED;

    @Override
    public IAnimalType getAnimalType() { return animalType; }

    @Override
    public String getName() { return name; }

    @Override
    public String getDate() { return name; }

    @Override
    public Gender getGender() { return gender; }

    @Override
    public String getFavoriteFood() { return favoriteFood; }

    @Override
    public int getId() { return id; }

    @Override
    public Optional<IEnclosure> getCurrentEnclosure() { return currentEnclosure; }

    @Override
    public Health getHealth() { return health; }

    @Override
    public Hunger getHunger() { return hunger; }

    @Override
    public void sick() {
        health = Health.SICK;
    }

    @Override
    public void hungry() {
        hunger = Hunger.HUNGRY;
    }

    @Override
    public boolean isSick() {
        return health == Health.SICK;
    }

    @Override
    public boolean isHungry() {
        return hunger == Hunger.HUNGRY;
    }


    public Animal(int id, IAnimalType animalType, String name, String date, Gender gender, String favoriteFood) {
        this.id = id;
        this.animalType = animalType;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.favoriteFood = favoriteFood;
    }

    @Override
    public void feed() {
        hunger = Hunger.FED;
    }

    @Override
    public void heal() {
        health = Health.HEALTHY;
    }

    @Override
    public void moveToEnclosure(IEnclosure enclosure) {
        currentEnclosure = Optional.of(enclosure);
    }
}
