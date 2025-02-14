package erp.domains.animals;

import erp.domains.Animal;
import erp.interfaces.IAnimalType;

public class Rabbit extends Animal {
    public Rabbit(IAnimalType animalType, int food, String identificationName) {
        super(animalType, food, identificationName);
    }
}
