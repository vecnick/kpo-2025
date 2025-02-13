package hse.studying.zoo.services;

import hse.studying.zoo.domains.Animal;
import hse.studying.zoo.domains.Thing;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Zoo is a service that provides operations for adding animals and things to the zoo, and for
 * retrieving all animals and things in the zoo.
 *
 * @see Clinic
 * @see Animal
 * @see Thing
 */
@Component
@ToString
public class Zoo {
    private final Clinic clinic;

    @Getter
    private final List<Animal> animals = new ArrayList<Animal>();

    @Getter
    private final List<Thing> things = new ArrayList<Thing>();

    /**
     * Constructs a Zoo with the specified clinic.
     *
     * @param clinic the clinic used to check animal health
     */
    public Zoo(Clinic clinic) {
        this.clinic = clinic;
    }

    /**
     * Adds an animal to the zoo if it is healthy.
     *
     * @param animal the animal to be added
     * @return true if the animal was added, false if it was not due to failing the health check
     */
    public boolean addAnimal(Animal animal) {
        if (clinic.isHealthy(animal)) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    /**
     * Adds a thing to the inventory of the zoo.
     *
     * @param thing the thing to be added
     */
    public void addThing(Thing thing) {
        things.add(thing);
    }
}
