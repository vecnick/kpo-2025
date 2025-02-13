package hse.studying.zoo.domains;

import hse.studying.zoo.params.HerbivoreParams;
import lombok.ToString;

/**
 * Class representing a monkey in the zoo.
 * A monkey is a herbivore.
 *
 * @see Herbo
 */
@ToString(callSuper = true)
public class Monkey extends Herbo {
    public Monkey(HerbivoreParams params) {
        super(params);
    }
}
