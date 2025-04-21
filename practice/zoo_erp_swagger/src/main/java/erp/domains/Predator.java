package erp.domains;

import erp.enums.FoodType;
import erp.interfaces.IAnimalType;

public class Predator implements IAnimalType {

    @Override
    public boolean isInteractiveAllowed() {
        return false;
    }

    @Override
    public boolean canEat(FoodType foodType) { return foodType == FoodType.MEAT; }
}
