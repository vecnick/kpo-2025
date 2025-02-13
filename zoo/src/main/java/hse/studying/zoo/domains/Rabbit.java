package hse.studying.zoo.domains;

import hse.studying.zoo.params.HerbivoreParams;
import lombok.ToString;

@ToString(callSuper = true)
public class Rabbit extends Herbo {
    public Rabbit(HerbivoreParams params) {
        super(params);
    }
}
