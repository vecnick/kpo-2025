package erp.interfaces;

import erp.domains.Enclosure;

public interface IEnclosureFactory {
    Enclosure create(IAnimalType animalType, int size, int maxAnimalsCount);
}
