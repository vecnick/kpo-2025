package hse.studying.zoo.domains;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class Predator extends Animal {
    public Predator(int foodConsumption, int inventoryNumber) {
        super(foodConsumption, inventoryNumber);
    }
}
