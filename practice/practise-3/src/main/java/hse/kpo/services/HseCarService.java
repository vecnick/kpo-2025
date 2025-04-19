package hse.kpo.services;

import hse.kpo.Enums.Types;
import hse.kpo.Observers.Sales;
import hse.kpo.Repository.CarRepository;
import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.interfaces.CarProviderI;
import hse.kpo.interfaces.CustomerProviderI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import hse.kpo.interfaces.SalesObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class HseCarService implements CarProviderI {

    private final List<SalesObserver> observers = new ArrayList<>();

    private final CustomerProviderI customerProvider;
    private final CarRepository carRepository;

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, Types productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }

    /**
     * Метод продажи машин
     */
    @Sales
    public void sellCars() {
        customerProvider.getCustomers().stream()
                .filter(customer -> customer.getCars() == null || customer.getCars().isEmpty())
                .forEach(customer -> {
                    Car car = takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.getCars().add(car); // Добавляем автомобиль в список клиента
                        car.setCustomer(customer);   // Устанавливаем ссылку на клиента в автомобиле
                        carRepository.save(car);     // Сохраняем изменения
                        notifyObserversForSale(customer, Types.CAR, car.getVin());
                    } else {
                        log.warn("No car in CarService");
                    }
                });
    }

    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = carRepository.findAll().stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(carRepository::delete);

        return firstCar.orElse(null);
    }

    /**
     * Метод добавления {@link Car} в систему.
     *
     * @param carFactory фабрика для создания автомобилей
     * @param carParams параметры для создания автомобиля
     */
    public <T> Car addCar(CarFactoryI<T> carFactory, T carParams) {
        return carRepository.save(carFactory.createCar(carParams));
    }

    public Car addExistingCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> findByVin(Integer vin) {
        return carRepository.findById(vin);
    }

    public void deleteByVin(Integer vin) {
        carRepository.deleteById(vin);
    }

    public List<Car> getCarsWithFiltration(String engineType, Integer vin) {
        return carRepository.findCarsByEngineTypeAndVinGreaterThan(engineType, vin);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }
}