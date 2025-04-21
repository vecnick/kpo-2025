package erp.storages;

import erp.factories.EnclosureFactory;
import erp.interfaces.IAnimalType;
import erp.interfaces.IEnclosure;

import java.util.List;

public class EnclosureStorage {
    private final EnclosureFactory enclosureFactory;
    private List<IEnclosure> enclosures;

    public List<IEnclosure> getEnclosures() {
        return enclosures;
    }

    public EnclosureStorage(EnclosureFactory enclosureFactory) {
        this.enclosureFactory = enclosureFactory;
    }

    public void add(IAnimalType animalType, int size, int maxAnimalsCount) {
        IEnclosure enclosure = enclosureFactory.create(animalType, size, maxAnimalsCount);
        enclosures.add(enclosure);
    }

    public void remove(int id) {
        enclosures.stream().filter(e -> e.getId() == id)
                .findFirst().ifPresent(enclosures::remove);
    }
}
