package hse.studying.zoo.domains;

import hse.studying.zoo.params.ThingParams;
import lombok.ToString;

/**
 * Class that represents a computer in zoo.
 */
@ToString(callSuper = true)
public class Computer extends Thing {
    public Computer(ThingParams params) {
        super(params);
    }
}
