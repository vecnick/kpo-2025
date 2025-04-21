package erp.interfaces;

import erp.domains.Animal;

import java.util.List;

public interface IEnclosure {
    
    int getAnimalsCount();
    int getMaxAnimalsCount();
    IAnimalType getAllowedAnimalType();
    int getId();
    List<IAlive> getAnimals();

    void add(IAlive animal);
    void remove(String name);
    void clean();
}
