package hse.studying.zoo.domains;

import hse.studying.zoo.params.PredatorParams;
import lombok.ToString;

@ToString(callSuper = true)
public class Tiger extends Predator {
    public Tiger(PredatorParams params) {
        super(params);
    }
}
