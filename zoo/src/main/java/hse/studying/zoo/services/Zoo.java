package hse.studying.zoo.services;

import hse.studying.zoo.domains.Animal;
import hse.studying.zoo.domains.Thing;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ToString
public class Zoo {
    private final Clinic clinic;

    @Getter
    private final List<Animal> animals = new ArrayList<Animal>();

    @Getter
    private final List<Thing> things = new ArrayList<Thing>();

    public Zoo(Clinic clinic) {
        this.clinic = clinic;
    }

    public boolean addAnimal(Animal animal) {
        if(clinic.isHealthy(animal)) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public void addThing(Thing thing) {
        things.add(thing);
    }
}
