package hse.studying.zoo.domains;

import hse.studying.zoo.params.PredatorParams;
import lombok.ToString;

/**
 * Abstract class for all predators.
 * A predator is an animal that feeds on meat.
 *
 * @see Animal
 */
@ToString(callSuper = true)
public abstract class Predator extends Animal {
    public Predator(PredatorParams params) {
        super(params.foodConsumption(), params.weight(), params.inventoryNumber());
    }
}
