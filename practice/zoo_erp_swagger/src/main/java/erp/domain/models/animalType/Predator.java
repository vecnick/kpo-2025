package erp.domain.models.animalType;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimalType;

public class Predator implements IAnimalType {

    @Override
    public boolean isInteractiveAllowed() {
        return false;
    }

    @Override
    public boolean canEat(FoodType foodType) { return foodType == FoodType.MEAT; }
}
