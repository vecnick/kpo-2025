package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Thing;
import hse.studying.zoo.params.ThingParams;
import org.springframework.stereotype.Component;

@Component
public class ThingFactory extends ZooFactory<ThingParams, Thing> {
}
