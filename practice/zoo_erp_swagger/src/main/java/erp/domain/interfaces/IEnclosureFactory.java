package erp.domain.interfaces;

import erp.domain.models.Enclosure;

import java.util.Optional;

public interface IEnclosureFactory {
    Optional<Enclosure> create(IAnimalType animalType, int size, int maxAnimalsCount);
}
