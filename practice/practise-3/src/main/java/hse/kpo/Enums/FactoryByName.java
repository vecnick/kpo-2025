package hse.kpo.Enums;

import hse.kpo.domains.params.EmptyEngineParams;
import hse.kpo.factories.Cars.HandCarFactoryI;
import hse.kpo.factories.Cars.PedalCarFactoryI;
import hse.kpo.interfaces.CarFactoryI;

public enum FactoryByName {
    PedalEngineI,
    HandEngineI;

    public static <T> CarFactoryI<T> getFactory(FactoryByName str) {
        return switch (str) {
            case PedalEngineI -> (CarFactoryI<T>) new PedalCarFactoryI();
            case HandEngineI -> (CarFactoryI<T>) new HandCarFactoryI();
            default -> null;
        };
    }
}
