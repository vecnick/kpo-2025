package hse.kpo.interfaces;

import hse.kpo.domains.Cars.Car;

/**
 * Интерфейс фабрики машин с определенными параметрами.
 *
 * @param <T> - параметры машины
 */
public interface CarFactoryI<T> {
    /**
     * Метод, позволяющий создать машину с определенными параметрами.
     *
     * @param carParams - параметры новой машины
     * @return - экземпляр класса Car с заданными параметрами
     */
    Car createCar(T carParams);
}
