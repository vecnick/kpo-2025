package hse.studying.zoo.factories;

import hse.studying.zoo.domains.Animal;
import hse.studying.zoo.params.HerbivoreParams;
import hse.studying.zoo.params.PredatorParams;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AnimalFactory {
    private static final Map<String, Function<?, Animal>> registry = new HashMap<>();

    public static void registerPredator(String type, Function<PredatorParams, Animal> creator) {
        if (registry.containsKey(type.toLowerCase())) {
            throw new IllegalArgumentException("Animal type already registered: " + type);
        }
        registry.put(type.toLowerCase(), creator);
    }

    public static void registerHerbivore(String type, Function<HerbivoreParams, Animal> creator) {
        if (registry.containsKey(type.toLowerCase())) {
            throw new IllegalArgumentException("Animal type already registered: " + type);
        }
        registry.put(type.toLowerCase(), creator);
    }

    public static Animal createAnimal(String type, Object params) {
        Function<?, Animal> creator = registry.get(type.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("Unknown animal type: " + type);
        }
        return ((Function<Object, Animal>) creator).apply(params);
    }
}
