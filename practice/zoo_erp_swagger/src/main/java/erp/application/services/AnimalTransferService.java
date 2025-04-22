package erp.application.services;

import erp.domain.models.Animal;
import erp.domain.models.Enclosure;


import static erp.shared.utils.CompareUtil.tryCompareRecursively;

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
