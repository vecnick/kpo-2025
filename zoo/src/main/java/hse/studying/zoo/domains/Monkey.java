package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public class Monkey extends Herbo {
    public Monkey(int foodConsumption, int inventoryNumber, int kindness) {
        super(foodConsumption, inventoryNumber, kindness);
    }
}
