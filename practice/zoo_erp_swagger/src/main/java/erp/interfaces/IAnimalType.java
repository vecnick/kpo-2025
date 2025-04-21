package erp.interfaces;

import erp.enums.FoodType;

public interface IAnimalType {
    boolean isInteractiveAllowed();
    boolean canEat(FoodType foodType);
}
