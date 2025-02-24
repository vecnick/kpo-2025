package hse.kpo.services;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CarProviderI;
import hse.kpo.interfaces.CustomerProviderI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hse.kpo.interfaces.SalesObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Класс, продающий автомобили.
 */
@Slf4j
@Component
public class HseShipService {

    @Autowired
    private final ShipService shipProvider;

    @Autowired
    private final CustomerProviderI customerProvider;

    /**
     * Конструктор класса HseCarService.
     *
     * @param carProvider       - класс, предоставляющий машины
     * @param customersProvider - класс, предоставляющий покупателей
     */
    public HseShipService(ShipService carProvider, CustomerProviderI customersProvider) {
        this.shipProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    final List<SalesObserver> observers = new ArrayList<>();

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, Types productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }

    /**
     * Метод, назначающий покупателям подходящие им машины.
     */
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getShip())).forEach(customer -> {
            var car = shipProvider.takeShip(customer);
            if (Objects.nonNull(car)) {
                notifyObserversForSale(customer, Types.SHIP, car.getVin());
                customer.setShip(car);
            } else {
                log.debug("No customer found for ship {}", customer.getShip());
            }
        });
    }
}