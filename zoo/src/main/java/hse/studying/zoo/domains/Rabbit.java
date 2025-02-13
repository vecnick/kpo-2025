package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public class Rabbit extends Herbo {
    public Rabbit(int foodConsumption, int inventoryNumber, int kindness) {
        super(foodConsumption, inventoryNumber, kindness);
    }
}
