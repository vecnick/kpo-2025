package erp.domains.animals;

import erp.domains.Animal;
import erp.interfaces.IAnimalType;

public class Tiger extends Animal {
    public Tiger(IAnimalType animalType, int food, String identificationName) {
        super(animalType, food, identificationName);
    }
}
