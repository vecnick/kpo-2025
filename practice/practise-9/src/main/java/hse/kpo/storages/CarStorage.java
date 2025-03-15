package hse.kpo.storages;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.cars.CarFactory;
import hse.kpo.interfaces.cars.CarProvider;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации о машинах.
 */
@Component
public class CarStorage implements CarProvider {

    @Getter
    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Метод добавления {@link Car} в систему.
     *
     * @param carFactory фабрика для создания автомобилей
     * @param carParams параметры для создания автомобиля
     */
    public <T> void addCar(CarFactory<T> carFactory, T carParams) {
        var car = carFactory.create(
                carParams,
                ++carNumberCounter
        );

        cars.add(car);
    }

    public boolean addExistingCar(Car car) {
        return cars.add(car);
    }
}
