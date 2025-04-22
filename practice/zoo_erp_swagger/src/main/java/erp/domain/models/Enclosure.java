package erp.domain.models;

import erp.domain.enums.Cleanness;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;

import java.util.List;
import java.util.Optional;

public class Enclosure implements IEnclosure {
    private final IAnimalType allowedAnimalType;
    private final int size;
    private int animalsCount = 0;
    private final int maxAnimalsCount;

    private final int id;
    private List<IAnimal> animals;
    private Cleanness cleanness = Cleanness.CLEAN;

    @Override
    public IAnimalType getAllowedAnimalType() { return allowedAnimalType; }

    @Override
    public int getSize() { return size; }

    @Override
    public int getAnimalsCount() { return animalsCount; }

    @Override
    public int getMaxAnimalsCount() { return maxAnimalsCount; }

    @Override
    public int getId() { return id; }

    @Override
    public List<IAnimal> getAnimals() { return animals; }

    @Override
    public Cleanness getCleanness() { return cleanness; }

    @Override
    public void dirty() {
        cleanness = Cleanness.DIRTY;
    }

    @Override
    public boolean isDirty() {
        return cleanness == Cleanness.DIRTY;
    }


    public Enclosure(int id, IAnimalType animalType, int size, int maxAnimalsCount) {
        this.id = id;
        this.allowedAnimalType = animalType;
        this.size = size;
        this.maxAnimalsCount = maxAnimalsCount;
    }

    @Override
    public void add(IAnimal animal) {
        animals.add(animal);
        animalsCount++;
    }

    @Override
    public void remove(String animalName) {
        Optional<IAnimal> match = animals.stream()
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

