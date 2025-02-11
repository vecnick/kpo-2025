package hse.kpo.services;

import hse.kpo.interfaces.CarProviderI;
import hse.kpo.interfaces.CustomerProviderI;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Класс, продающий автомобили.
 */
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
        customers.stream().filter(customer -> Objects.isNull(customer.getCar())).forEach(customer -> {
            var car = shipProvider.takeShip(customer);
            if (Objects.nonNull(car)) {
                customer.setShip(car);
            }
        });
    }
}