package hse.kpo.services;

import hse.kpo.interfaces.CarProviderI;
import hse.kpo.interfaces.CustomerProviderI;
import java.util.Objects;

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
                customer.setShip(car);
            } else {
                log.warn("No customer found for ship {}", customer.getShip());
            }
        });
    }
}