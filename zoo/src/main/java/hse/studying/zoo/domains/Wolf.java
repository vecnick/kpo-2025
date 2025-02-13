package hse.studying.zoo.domains;

import hse.studying.zoo.params.PredatorParams;
import lombok.ToString;

@ToString(callSuper = true)
public class Wolf extends Predator {
    public Wolf(PredatorParams params) {
        super(params);
    }
}
