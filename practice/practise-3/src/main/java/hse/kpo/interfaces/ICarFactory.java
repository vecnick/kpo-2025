package hse.kpo.interfaces;

import hse.kpo.domains.Car;

public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}
