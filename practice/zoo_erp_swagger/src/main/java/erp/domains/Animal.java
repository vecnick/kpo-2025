package erp.domains;

import erp.enums.Gender;
import erp.enums.Health;
import erp.enums.Hunger;
import erp.interfaces.IAlive;
import erp.interfaces.IAnimalType;
import erp.interfaces.IEnclosure;
import lombok.ToString;

import java.util.Optional;

@ToString
public class Animal implements IAlive {
    private final IAnimalType animalType;
    private final String name;
    private final String date;
    private final Gender gender;
    private String favoriteFood;

    private Optional<IEnclosure> currentEnclosure = Optional.empty();
    private Health health = Health.HEALTHY;
    private Hunger hunger= Hunger.FED;

    public void sick() {
        health = Health.SICK;
    }

    public void hungry() {
        hunger = Hunger.HUNGRY;
    }

    @Override
    public IAnimalType getAnimalType() {
        return animalType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<IEnclosure> getCurrentEnclosure() {
        return currentEnclosure;
    }

    public Animal(IAnimalType animalType, String name, String date, Gender gender, String favoriteFood) {
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
