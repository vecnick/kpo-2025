package studying.interfaces;

import studying.domains.Car;

public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}
