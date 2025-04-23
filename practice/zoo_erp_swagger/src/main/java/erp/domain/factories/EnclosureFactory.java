package erp.domain.factories;

import erp.domain.models.Enclosure;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosureFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EnclosureFactory implements IEnclosureFactory {
    private int lastId = 0;

    @Override
    public Optional<Enclosure> create(IAnimalType animalType, int size, int maxAnimalsCount) {
        if (size < 0) {
//            throw new IllegalArgumentException("Размер не может быть отрицательным");
            return Optional.empty();
        }
        if (maxAnimalsCount < 0) {
//            throw new IllegalArgumentException("Максимальное количество животных не может быть отрицательным");
            return Optional.empty();
        }
        return Optional.of(new Enclosure(lastId++, animalType, size, maxAnimalsCount));
    }
}
