package erp.domain.interfaces;

import erp.domain.enums.Cleanness;

import java.util.List;

public interface IEnclosure {

    IAnimalType getAllowedAnimalType();
    int getSize();
    int getAnimalsCount();
    int getMaxAnimalsCount();
    int getId();
    List<IAnimal> getAnimals();
    Cleanness getCleanness();

    void dirty();
    boolean isDirty();

    void add(IAnimal animal);
    void remove(String name);
    void clean();
}
