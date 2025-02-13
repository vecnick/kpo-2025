package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public class Wolf extends Predator {
    public Wolf(int foodConsumption, int inventoryNumber) {
        super(foodConsumption, inventoryNumber);
    }
}
