package erp.infrastructure.interfaces;

import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import erp.domain.models.Enclosure;

import java.util.List;
import java.util.Optional;

public interface IEnclosureStorage {

    List<IEnclosure> getEnclosures();
    Optional<Enclosure> add(IAnimalType animalType, int size, int maxAnimalsCount);
    boolean remove(int id);
}
