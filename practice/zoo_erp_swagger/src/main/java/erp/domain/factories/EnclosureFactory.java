package erp.domain.factories;

import erp.domain.models.Enclosure;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosureFactory;

public class EnclosureFactory implements IEnclosureFactory {
    private int lastId = 0;

    @Override
    public Enclosure create(IAnimalType animalType, int size, int maxAnimalsCount) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер не может быть отрицательным");
        }
        if (maxAnimalsCount < 0) {
            throw new IllegalArgumentException("Максимальное количество животных не может быть отрицательным");
        }
        return new Enclosure(lastId++, animalType, size, maxAnimalsCount);
    }
}
