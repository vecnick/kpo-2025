package hse.studying.zoo.domains;

import hse.studying.zoo.params.HerbivoreParams;
import lombok.ToString;

@ToString(callSuper = true)
public class Monkey extends Herbo {
    public Monkey(HerbivoreParams params) {
        super(params);
    }
}
