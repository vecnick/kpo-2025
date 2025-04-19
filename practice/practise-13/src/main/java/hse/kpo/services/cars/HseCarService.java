package hse.kpo.services.cars;

import hse.kpo.domains.Customer;
import hse.kpo.domains.cars.Car;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.cars.CarFactory;
import hse.kpo.observers.Sales;
import hse.kpo.observers.SalesObserver;
import hse.kpo.interfaces.cars.CarProvider;
import hse.kpo.interfaces.CustomerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import hse.kpo.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис продажи машин.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HseCarService implements CarProvider{

    private final List<SalesObserver> observers = new ArrayList<>();

    private final CustomerProvider customerProvider;
    private final CarRepository carRepository;

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, ProductionTypes productType, int vin) {
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
                    notifyObserversForSale(customer, ProductionTypes.CAR, car.getVin());
                } else {
                    log.warn("No car in CarService");
                }
            });
    }

    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = carRepository.findAll().stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        return firstCar.orElse(null);
    }

    /**
     * Метод добавления {@link Car} в систему.
     *
     * @param carFactory фабрика для создания автомобилей
     * @param carParams параметры для создания автомобиля
     */
    public <T> Car addCar(CarFactory<T> carFactory, T carParams) {
        return carRepository.save(carFactory.create(carParams));
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