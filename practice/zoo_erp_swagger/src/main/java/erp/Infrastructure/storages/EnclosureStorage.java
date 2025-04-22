package erp.Infrastructure.storages;

import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IEnclosureFactory;
import erp.Infrastructure.interfaces.IEnclosureStorage;

import java.util.List;

public class EnclosureStorage implements IEnclosureStorage {
    private final IEnclosureFactory enclosureFactory;
    private List<IEnclosure> enclosures;

    @Override
    public List<IEnclosure> getEnclosures() {
        return enclosures;
    }

    public EnclosureStorage(IEnclosureFactory enclosureFactory) {
        this.enclosureFactory = enclosureFactory;
    }

    @Override
    public void add(IAnimalType animalType, int size, int maxAnimalsCount) {
        IEnclosure enclosure = enclosureFactory.create(animalType, size, maxAnimalsCount);
        enclosures.add(enclosure);
    }

    @Override
    public void remove(int id) {
        enclosures.stream().filter(e -> e.getId() == id)
                .findFirst().ifPresent(enclosures::remove);
    }
}
