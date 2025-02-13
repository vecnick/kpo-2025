package hse.studying.zoo.domains;

import hse.studying.zoo.params.HerbivoreParams;
import lombok.Getter;
import lombok.ToString;

/**
 * Abstract class for herbivores.
 * Herbivores have a 'kindness' parameter in addition to food consumption, weight, and inventory number.
 *
 * @see Animal
 */
@Getter
@ToString(callSuper = true)
public abstract class Herbo extends Animal {
    private final int kindness;

    /**
     * Constructor for herbivores.
     *
     * @param params parameters for the herbivore
     */
    public Herbo(HerbivoreParams params) {
        super(params.foodConsumption(), params.weight(), params.inventoryNumber());
        if (params.kindness() < 0 || params.kindness() > 10) {
            throw new IllegalArgumentException("Kindness must be between 0 and 10");
        }
        kindness = params.kindness();
    }

}
