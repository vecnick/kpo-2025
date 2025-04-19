package zooapi.zooerp2.Domain.Enums;

import java.util.Arrays;
import java.util.Optional;

public enum AnimalType {
    PREDATOR("PREDATOR"),
    HERBO("HERBO"),
    BIRD("BIRD"),
    FISH("FISH");

    private final String name;

    AnimalType(String name) {
        this.name = name;
    }

    public static Optional<AnimalType> find(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findFirst();
    }
}
