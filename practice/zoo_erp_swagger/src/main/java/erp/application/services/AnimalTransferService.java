package erp.application.services;

import erp.application.interfaces.IAnimalTransferService;
import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IEnclosure;
import erp.domain.models.Animal;
import erp.domain.models.Enclosure;
import org.springframework.stereotype.Service;


import static erp.shared.utils.CompareUtil.tryCompareRecursively;

@Service
public class AnimalTransferService implements IAnimalTransferService {

    @Override
    public boolean transfer(IEnclosure enclosure, IAnimal animal) {

        // Можно ли поместить животное в данный вольер?
        if ((enclosure.getAnimalsCount() >= enclosure.getMaxAnimalsCount()) ||
            (enclosure.getAllowedAnimalType().canEat(FoodType.MEAT) != animal.getAnimalType().canEat(FoodType.MEAT)) ||
            (enclosure.getAllowedAnimalType().isInteractiveAllowed() != animal.getAnimalType().isInteractiveAllowed())) {
            return false;
        }

        // Находится ли животное уже в каком-то другом вольере?
        if (animal.getCurrentEnclosure().isPresent()) {
            animal.getCurrentEnclosure().get().remove(animal.getId());
        }

        enclosure.add(animal);
        animal.moveToEnclosure(enclosure);
        return true;
    }
}
