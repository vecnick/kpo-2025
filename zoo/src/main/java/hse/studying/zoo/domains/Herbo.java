package hse.studying.zoo.domains;

import lombok.ToString;

@ToString
public abstract class Herbo extends Animal {
    private final int kindness;

    public Herbo(int foodConsumption, int inventoryNumber, int kindness) {
        super(foodConsumption, inventoryNumber);
        this.kindness = kindness;
    }


}
