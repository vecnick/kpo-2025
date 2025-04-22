package erp.domain.models.animalType;

import erp.domain.enums.FoodType;
import erp.domain.interfaces.IAnimalType;

public class Herbo implements IAnimalType {
    private int kindness;

    public Herbo(int kindness) { this.kindness = kindness; }

    @Override
    public boolean isInteractiveAllowed() {
        return kindness > 5;
    }

    @Override
    public boolean canEat(FoodType foodType) { return foodType == FoodType.PLANT; }
}
