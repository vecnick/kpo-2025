package erp.domain.interfaces;

import erp.domain.enums.FoodType;

public interface IAnimalType {
    boolean isInteractiveAllowed();
    boolean canEat(FoodType foodType);
}
