package hse.kpo.Enums;

import hse.kpo.domains.Cars.Car;
import hse.kpo.factories.Ships.ShipFactory;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.Enums.FactoryByName;
import hse.kpo.interfaces.EngineI;

public enum Types {
    CAR("CAR"),
    SHIP("SHIP");

    private final String typeName;

    Types(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

    // Метод для получения enum по строке
    public static Types fromString(String str) {
        for (Types type : Types.values()) {
            if (type.typeName.equalsIgnoreCase(str)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant found for: " + str);
    }
}
