package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Herbo;
import hse.studying.zoo.params.HerbivoreParams;
import org.springframework.stereotype.Component;

/**
 * HerbivoreFactory is a concrete factory for creating herbivore animals.
 * It extends the ZooFactory to provide specific implementation for creating
 * objects of the Herbo type with HerbivoreParams.
 *
 * @see ZooFactory
 * @see Herbo
 * @see HerbivoreParams
 */
@Component
public class HerbivoreFactory extends ZooFactory<HerbivoreParams, Herbo> {
}
