package hse.kpo.interfaces;

import hse.kpo.domains.Car;

/**
 * generic-интерфейс автомобильной фабрики
 *
 * @param <TParams> - параметры двигателя
 */
public interface ICarFactory<TParams> {
    /**
     * Создаёт автомобиль с определённым типом двигателя
     *
     * @param carParams - параметры автомобиля
     * @param carNumber - порядковый номер автомобиля на производстве
     * @return бъект автомобиля с двигателем определённого типа
     */
    Car createCar(TParams carParams, int carNumber);
}
