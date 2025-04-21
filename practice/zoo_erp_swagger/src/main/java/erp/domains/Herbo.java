package erp.domains;

import erp.enums.FoodType;
import erp.interfaces.IAnimalType;

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
