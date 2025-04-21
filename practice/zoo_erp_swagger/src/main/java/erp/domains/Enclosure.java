package erp.domains;

import erp.enums.Cleanness;
import erp.interfaces.IAlive;
import erp.interfaces.IAnimalType;
import erp.interfaces.IEnclosure;

import java.util.List;
import java.util.Optional;

import static erp.utils.CompareUtil.tryCompareRecursively;

public class Enclosure implements IEnclosure {
    private final IAnimalType allowedAnimalType;
    private final int size;
    private int animalsCount = 0;
    private final int maxAnimalsCount;

    private final int id;
    private List<IAlive> animals;
    private Cleanness cleanness = Cleanness.CLEAN;

    public void dirty() {
        cleanness = Cleanness.DIRTY;
    }

    @Override
    public int getAnimalsCount() {
        return animalsCount;
    }

    @Override
    public int getMaxAnimalsCount() {
        return maxAnimalsCount;
    }

    @Override
    public IAnimalType getAllowedAnimalType() {
        return allowedAnimalType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<IAlive> getAnimals() {
        return animals;
    }

    public Enclosure(int id, IAnimalType animalType, int size, int maxAnimalsCount) {
        this.id = id;
        this.allowedAnimalType = animalType;
        this.size = size;
        this.maxAnimalsCount = maxAnimalsCount;
    }

    @Override
    public void add(IAlive animal) {
        animals.add(animal);
        animalsCount++;
    }

    @Override
    public void remove(String animalName) {
        Optional<IAlive> match = animals.stream()
                .filter(animal -> animal.getName().equals(animalName)).findFirst();
        if (match.isPresent()) {
            throw new IllegalArgumentException("Животного с таким именем нет в вольере");
        }
        animals.remove(match.get());
        animalsCount--;
    }

    @Override
    public void clean() {
        cleanness = Cleanness.CLEAN;
    }
}

