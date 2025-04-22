package erp.presentation.responses;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimalType;

public record AnimalTypeResponse(
    boolean isInteractiveAllowed,
    FoodType foodType
) {
    public AnimalTypeResponse(IAnimalType animalType) {
        this(animalType.isInteractiveAllowed(),
            animalType.canEat(FoodType.MEAT) ? FoodType.MEAT : FoodType.PLANT);
    }
}
