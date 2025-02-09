package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.interfaces.CarProviderI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс поставщика машин.
 */
@Component
@RequiredArgsConstructor
public class CarServiceI implements CarProviderI {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     * Функция дающая машину покупателю.
     *
     * @param customer - покупатель, для которого ищем машину
     * @return машину или null.
     */
    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Добавить машину.
     *
     * @param carFactory - фабрика поставщик
     * @param carParams - параметры новой машины
     * @param <T> - тип параметров машины
     */
    public <T> void addCar(CarFactoryI<T> carFactory, T carParams) {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(
                carParams, // передаем параметры
                ++carNumberCounter // передаем номер - номер будет начинаться с 1
        );

        cars.add(car); // добавляем автомобиль
    }
}
