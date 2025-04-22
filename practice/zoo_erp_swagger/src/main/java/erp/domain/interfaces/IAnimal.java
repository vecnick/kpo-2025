package erp.domain.interfaces;

import erp.domain.enums.Gender;
import erp.domain.enums.Health;
import erp.domain.enums.Hunger;

import java.util.Optional;

public interface IAnimal {

    IAnimalType getAnimalType();
    String getName();
    String getDate();
    Gender getGender();
    String getFavoriteFood();
    int getId();
    Optional<IEnclosure> getCurrentEnclosure();
    Health getHealth();
    Hunger getHunger();

    void sick();
    void hungry();
    boolean isSick();
    boolean isHungry();

    void feed();
    void heal();
    void moveToEnclosure(IEnclosure enclosure);
}

