package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Predator;
import hse.studying.zoo.params.PredatorParams;
import org.springframework.stereotype.Component;

@Component
public class PredatorFactory extends ZooFactory<PredatorParams, Predator> {
}
