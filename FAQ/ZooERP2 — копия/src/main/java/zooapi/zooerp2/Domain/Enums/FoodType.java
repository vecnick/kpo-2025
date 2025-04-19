package zooapi.zooerp2.Domain.Enums;

import java.util.Arrays;
import java.util.Optional;

public enum FoodType {
    MEAT("MEAT"),
    HERB("HERB"),
    FISHFOOD("FISHFOOD"),
    INSECTS("INSECTS"),;

    private final String name;

    FoodType(String name) {
        this.name = name;
    }

    public static Optional<FoodType> find(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findFirst();
    }
}

