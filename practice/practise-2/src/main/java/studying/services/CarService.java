package studying.services;

import studying.domains.Car;
import studying.domains.Customer;
import studying.interfaces.ICarFactory;
import studying.interfaces.ICarProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управления автомобилями
 * имеет коллекцию машин (cars) и текущий порядковой номер изготовленной машины (carNumberCounter)
 */
public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     * Подор автомобиля для покупателя
     *
     * @param customer - покупатель, под которого подбираем автомобиль
     * @return первый попавшийся автомобиль по параметрам пользователя или null, если ничего найти не удалось
     */
    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Добавить автомобиль в коллекцию
     *
     * @param carFactory - фабрика на которой изготавливается автомобиль
     * @param carParams - параметры, по которым автомобиль будет создан
     * @param <TParams> - тип параметров автомобиля
     */
    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams)
    {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(
                carParams, // передаем параметры
                ++carNumberCounter // передаем номер - номер будет начинаться с 1
        );

        cars.add(car); // добавляем автомобиль
    }
}
