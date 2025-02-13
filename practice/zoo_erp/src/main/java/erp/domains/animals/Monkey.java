package erp.domains.animals;

import erp.domains.Animal;
import erp.interfaces.IAnimalType;

public class Monkey extends Animal {
    public Monkey(IAnimalType animalType, int food, String identificationName) {
        super(animalType, food, identificationName);
    }
}
