package hse.studying.zoo.domains;

import hse.studying.zoo.params.HerbivoreParams;
import lombok.ToString;

/**
 * Class representing a rabbit in the zoo.
 * A rabbit is a herbivore.
 *
 * @see Herbo
 */
@ToString(callSuper = true)
public class Rabbit extends Herbo {
    public Rabbit(HerbivoreParams params) {
        super(params);
    }
}
