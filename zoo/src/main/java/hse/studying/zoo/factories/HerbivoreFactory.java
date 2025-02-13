package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Herbo;
import hse.studying.zoo.params.HerbivoreParams;
import org.springframework.stereotype.Component;

@Component
public class HerbivoreFactory extends ZooFactory<HerbivoreParams, Herbo> {
}
