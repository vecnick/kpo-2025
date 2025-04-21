package erp.domains;

import erp.enums.Cleanness;
import erp.interfaces.IAlive;
import erp.interfaces.IAnimalType;
import erp.interfaces.IEnclosure;

public class Enclosure implements IEnclosure {
    private final IAnimalType animalType;
    private final int size;
    private final int maxAnimalsCount;

    private int animalsCount = 0;
    private Cleanness cleanness = Cleanness.CLEAN;

    public void dirty() {
        cleanness = Cleanness.DIRTY;
    }


    public Enclosure(IAnimalType animalType, int size, int maxAnimalsCount) {
        this.animalType = animalType;
        this.size = size;
        this.maxAnimalsCount = maxAnimalsCount;
    }

    @Override
    public void add(IAlive animal) {
        animalsCount++;
    }

    @Override
    public void remove(String animalName) {
        animalsCount--;
    }

    @Override
    public void clean() {
        cleanness = Cleanness.CLEAN;
    }

}

