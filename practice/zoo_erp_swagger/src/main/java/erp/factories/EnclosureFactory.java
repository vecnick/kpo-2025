package erp.factories;

import erp.domains.Enclosure;
import erp.interfaces.IAnimalType;
import erp.interfaces.IEnclosureFactory;

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
