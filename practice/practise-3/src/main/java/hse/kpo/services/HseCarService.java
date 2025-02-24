package hse.kpo.services;

import hse.kpo.Enums.Types;
import hse.kpo.Observers.Sales;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CarProviderI;
import hse.kpo.interfaces.CustomerProviderI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hse.kpo.interfaces.SalesObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Класс, продающий автомобили.
 */
@Component
@Slf4j
public class HseCarService {

    @Autowired
    private final CarProviderI carProvider;

    @Autowired
    private final CustomerProviderI customerProvider;

    final List<SalesObserver> observers = new ArrayList<>();

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, Types productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }

    /**
     * Конструктор класса HseCarService.
     *
     * @param carProvider       - класс, предоставляющий машины
     * @param customersProvider - класс, предоставляющий покупателей
     */
    public HseCarService(CarProviderI carProvider, CustomerProviderI customersProvider) {
        this.carProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    /**
     * Метод, назначающий покупателям подходящие им машины.
     */
    @Sales
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar())).forEach(customer -> {
            var car = carProvider.takeCar(customer);
            if (Objects.nonNull(car)) {
                notifyObserversForSale(customer, Types.CAR, car.getVin());
                customer.setCar(car);
                log.debug("found car {} for customer {}", car, customer);
            }
        });
    }
}