package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Thing;
import hse.studying.zoo.params.ThingParams;
import org.springframework.stereotype.Component;

/**
 * ThingFactory is a concrete factory for creating things.
 * It extends the ZooFactory to provide specific implementation for creating
 * objects of the Thing type with ThingParams.
 *
 * @see ZooFactory
 * @see Thing
 * @see ThingParams
 */
@Component
public class ThingFactory extends ZooFactory<ThingParams, Thing> {
}
