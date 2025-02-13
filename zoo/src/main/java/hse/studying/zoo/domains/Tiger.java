package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public class Tiger extends Predator {
    public Tiger(int foodConsumption, int inventoryNumber) {
        super(foodConsumption, inventoryNumber);
    }
}
