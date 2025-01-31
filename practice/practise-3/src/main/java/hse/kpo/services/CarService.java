package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.interfaces.ICarProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Автосервис
 */
public class CarService implements ICarProvider {
    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     * Выдаёт покупателю машину
     *
     * @param customer покупатель
     * @return машина
     */
    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Добавляет машину в сервис
     *
     * @param carFactory фабрика машин
     * @param carParams параметры машин
     */
    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams) // Пример использования подстановки Лисков
    {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(
                carParams, // передаем параметры
                ++carNumberCounter // передаем номер - номер будет начинаться с 1
        );

        cars.add(car); // добавляем автомобиль
    }
}
