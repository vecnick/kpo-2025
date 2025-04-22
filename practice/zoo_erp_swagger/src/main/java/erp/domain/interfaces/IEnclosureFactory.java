package erp.domain.interfaces;

import erp.domain.models.Enclosure;

public interface IEnclosureFactory {
    Enclosure create(IAnimalType animalType, int size, int maxAnimalsCount);
}
