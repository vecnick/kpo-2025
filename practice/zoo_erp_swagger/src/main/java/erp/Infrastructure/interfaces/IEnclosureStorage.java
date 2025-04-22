package erp.Infrastructure.interfaces;

import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;

import java.util.List;

public interface IEnclosureStorage {

    List<IEnclosure> getEnclosures();
    public void add(IAnimalType animalType, int size, int maxAnimalsCount);
    public void remove(int id);
}
