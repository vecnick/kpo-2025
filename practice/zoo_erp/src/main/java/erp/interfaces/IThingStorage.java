package erp.interfaces;

import erp.domains.Thing;

import java.util.List;

public interface IThingStorage {
    public List<Thing> getThings();
    public boolean addThing(Thing thing);
    public Thing takeThing(Thing thing);
    public Thing takeThing(int number);
}
