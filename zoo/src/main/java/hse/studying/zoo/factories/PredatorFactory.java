package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Predator;
import hse.studying.zoo.params.PredatorParams;
import org.springframework.stereotype.Component;

/**
 * PredatorFactory is a concrete factory for creating predators.
 * It extends the ZooFactory to provide specific implementation for creating
 * objects of the Predator type with PredatorParams.
 *
 * @see ZooFactory
 * @see Predator
 * @see PredatorParams
 */
@Component
public class PredatorFactory extends ZooFactory<PredatorParams, Predator> {
}
