package hse.kpo.enums;

import java.util.Arrays;
import java.util.Optional;

public enum EngineTypes {
    HAND ("HAND"),
    PEDAL ("PEDAL"),
    LEVITATION ("LEVITATION");

    private final String name;

    EngineTypes(String name) {
        this.name = name;
    }

    public static Optional<EngineTypes> find(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findFirst();
    }
}
