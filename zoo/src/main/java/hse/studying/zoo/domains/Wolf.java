package hse.studying.zoo.domains;

import hse.studying.zoo.params.PredatorParams;
import lombok.ToString;

/**
 * Class representing a wolf in the zoo.
 * A wolf is a predator.
 *
 * @see Predator
 */
@ToString(callSuper = true)
public class Wolf extends Predator {
    public Wolf(PredatorParams params) {
        super(params);
    }
}
