package zooapi.zooerp2.Domain.Enums;

import java.util.Arrays;
import java.util.Optional;

public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String name;
    Sex(String name) {
        this.name = name;
    }

    public static Optional<Sex> find(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findFirst();
    }
}
