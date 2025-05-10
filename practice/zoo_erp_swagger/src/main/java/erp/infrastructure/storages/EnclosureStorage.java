package erp.infrastructure.storages;

import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IEnclosureFactory;
import erp.infrastructure.interfaces.IEnclosureStorage;
import erp.domain.models.Enclosure;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnclosureStorage implements IEnclosureStorage {
    private final IEnclosureFactory enclosureFactory;
    private List<IEnclosure> enclosures = new ArrayList<>();

    @Override
    public List<IEnclosure> getEnclosures() {
        return enclosures;
    }

    public EnclosureStorage(IEnclosureFactory enclosureFactory) {
        this.enclosureFactory = enclosureFactory;
    }

    @Override
    public Optional<Enclosure> add(IAnimalType animalType, int size, int maxAnimalsCount) {
        Optional<Enclosure> enclosure = enclosureFactory.create(animalType, size, maxAnimalsCount);
        if (enclosure.isPresent()) {
            enclosures.add(enclosure.get());
        }
        return enclosure;
    }

    @Override
    public boolean remove(int id) {
        return enclosures.removeIf(enclosure -> enclosure.getId() == id);
    }
}
