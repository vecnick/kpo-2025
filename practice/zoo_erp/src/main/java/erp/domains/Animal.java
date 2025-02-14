package erp.domains;

import erp.interfaces.IAlive;
import erp.interfaces.IAnimalType;
import lombok.ToString;

@ToString
public class Animal implements IAlive {
    private final IAnimalType animalType;
    private final int food;
    private final String identificationName;

    @Override
    public int getFood() {
        return food;
    }

    public String getIdentificationName() { return identificationName; }

    public Animal(IAnimalType animalType, int food, String identificationName) {
        this.animalType = animalType;
        this.food = food;
        this.identificationName = identificationName;
    }

    public boolean isInteractiveAllowed() {
        return animalType.isInteractiveAllowed();
    }
}
