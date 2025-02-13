package hse.studying.zoo.services;

import hse.studying.zoo.domains.Animal;
import org.springframework.stereotype.Service;

/**
 * This is a clinic service.
 * It provides a method to check if the animal is healthy or not.
 * An animal is healthy if its food consumption is more than 10% of its weight.
 */
@Service
public class Clinic {
    public boolean isHealthy(Animal animal) {
        return animal.getFoodConsumption() >= animal.getWeight() * 0.1;
    }
}
