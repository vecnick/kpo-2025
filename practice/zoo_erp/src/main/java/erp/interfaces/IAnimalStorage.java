package erp.interfaces;

import erp.domains.Animal;

import java.util.List;

public interface IAnimalStorage {
    public List<Animal> getAnimals();
    public boolean addAnimal(Animal animal);
    public Animal takeAnimal(Animal animal);
    public Animal takeAnimal(String identificationName);
}
