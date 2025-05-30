package hse.kpo.interfaces.cars;

import hse.kpo.domains.cars.Car;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <T> параметры для фабрик
 */
public interface CarFactory<T> {
    /**
     * Метод создания машин.
     *
     * @param carParams параметры для создания
     * @return {@link Car}
     */
    Car create(T carParams);
}
