package hse.studying.zoo.factories;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Abstract factory class for creating objects of type TObject with parameters of type TParams.
 * This class maintains a registry of type identifiers and their corresponding object creation functions.
 *
 * @param <ParamsT> the type of parameters required to create an object
 * @param <ObjectT> the type of object to be created
 */
public abstract class ZooFactory<ParamsT, ObjectT> {
    protected final Map<String, Function<ParamsT, ObjectT>> registry = new HashMap<>();

    /**
     * Registers a new type and its corresponding object creation function.
     *
     * @param type    the type identifier of the object
     * @param creator the function that creates an object of the specified type
     * @throws IllegalArgumentException if the type is already registered
     */
    public void register(String type, Function<ParamsT, ObjectT> creator) {
        if (registry.containsKey(type.toLowerCase())) {
            throw new IllegalArgumentException(type + " is already registered!");
        }
        registry.put(type.toLowerCase(), creator);
    }

    /**
     * Creates an object of the specified type using the provided parameters.
     *
     * @param type   the type identifier of the object to be created
     * @param params the parameters required to create the object
     * @return the created object
     * @throws IllegalArgumentException if no factory is registered for the specified type
     */
    public ObjectT create(String type, ParamsT params) {
        Function<ParamsT, ObjectT> creator = registry.get(type.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("No registered factory for: " + type);
        }
        return creator.apply(params);
    }
}
