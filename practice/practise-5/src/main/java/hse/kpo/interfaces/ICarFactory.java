package hse.kpo.interfaces;

import hse.kpo.domains.Car;
import lombok.experimental.UtilityClass;

public interface ICarFactory<TParams> {
    Car create(TParams carParams, int carNumber);
}
