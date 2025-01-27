package studying;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A service that provides cars to customers.
 */
public class CarService implements ICarProvider{

    /**
     * A list of available cars.
     */
    private final List<Car> cars = new ArrayList<>();

    /**
     * A counter for car numbers.
     */
    private int carNumberCounter = 0;

    /**
     * Takes a car from the list that is compatible with the customer.
     * @param customer the customer to check
     * @return the car if it is compatible, null otherwise
     */
    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Adds a car to the list based on the provided factory and parameters.
     * @param carFactory the factory to use
     * @param carParams the parameters to pass to the factory
     * @param <TParams> the type of parameters
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

