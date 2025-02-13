package hse.studying.zoo.domains;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
public abstract class Predator extends Animal {
    public Predator(int foodConsumption, int inventoryNumber) {
        super(foodConsumption, inventoryNumber);
    }
}
