package erp.services;

import erp.domains.Thing;
import erp.interfaces.IThingStorage;

import java.util.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.stereotype.Component;

@Component
public class ZooThingStorage implements IThingStorage {
    private final List<Thing> things = new ArrayList<>();

    @Override
    public List<Thing> getThings() {
        return things;
    }

    @Override
    public boolean addThing(Thing thing) {
        things.add(thing);
        return true;
    }

    @Override
    public Thing takeThing(Thing thing) {
        var matchedThing = things.stream()
                .filter(thingInList ->  EqualsBuilder.reflectionEquals(thingInList, thing))
                .findFirst();

        matchedThing.ifPresent(things::remove);

        return matchedThing.orElse(null);
    }

    @Override
    public Thing takeThing(int number) {
        var matchedThing = things.stream()
                .filter(thing -> thing.getNumber() == number)
                .findFirst();

        matchedThing.ifPresent(things::remove);

        return matchedThing.orElse(null);
    }
}
