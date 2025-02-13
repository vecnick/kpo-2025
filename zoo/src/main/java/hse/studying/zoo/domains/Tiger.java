package hse.studying.zoo.domains;

import hse.studying.zoo.params.PredatorParams;
import lombok.ToString;

/**
 * Class representing a tiger in the zoo.
 * A tiger is a predator.
 *
 * @see Predator
 */
@ToString(callSuper = true)
public class Tiger extends Predator {
    public Tiger(PredatorParams params) {
        super(params);
    }
}
