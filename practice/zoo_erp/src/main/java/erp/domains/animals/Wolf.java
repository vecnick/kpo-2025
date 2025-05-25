package erp.domains.animals;

import erp.domains.Animal;
import erp.interfaces.IAnimalType;

public class Wolf extends Animal {
    public Wolf(IAnimalType animalType, int food, String identificationName) {
        super(animalType, food, identificationName);
    }
}
