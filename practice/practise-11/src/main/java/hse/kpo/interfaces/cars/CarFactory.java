package hse.kpo.interfaces.cars;

import hse.kpo.domains.cars.Car;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <T> параметры для фабрик
 */
public interface CarFactory<T> {
    Car create(T parameters);
}
