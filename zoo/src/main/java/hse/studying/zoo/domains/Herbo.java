package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public abstract class Herbo extends Animal {
    private final int kindness;

    public Herbo(int foodConsumption, int inventoryNumber, int kindness) {
        super(foodConsumption, inventoryNumber);
        this.kindness = kindness;
    }

    public boolean isInteractable() {
        return kindness > 5;
    }

}
