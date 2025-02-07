package hse.kpo.interfaces;

import hse.kpo.domains.Car;

/**
 * Интерфейс фабрики машин.
 */
public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}
