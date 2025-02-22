package hse.kpo.interfaces;

import hse.kpo.domains.Car;

public interface IShipFactory<TParams> {
    Car create(TParams carParams, int carNumber);
}
