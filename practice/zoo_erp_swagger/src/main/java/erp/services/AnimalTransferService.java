package erp.services;

import erp.domains.Animal;
import erp.domains.Enclosure;


import static erp.utils.CompareUtil.tryCompareRecursively;

public class AnimalTransferService {

    public boolean transfer(Enclosure enclosure, Animal animal) {

        // Можно ли поместить животное в данный вольер?
        if (enclosure.getAnimalsCount() >= enclosure.getMaxAnimalsCount() ||
                !tryCompareRecursively(animal.getAnimalType(), enclosure.getAllowedAnimalType())) {
            return false;
        }

        // Находится ли животное уже в каком-то другом вольере?
        if (animal.getCurrentEnclosure().isPresent()) {
            animal.getCurrentEnclosure().get().remove(animal.getName());
        }

        enclosure.add(animal);
        animal.moveToEnclosure(enclosure);
        return true;
    }
}
