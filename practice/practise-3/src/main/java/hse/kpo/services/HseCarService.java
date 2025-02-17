package hse.kpo.services;

import hse.kpo.interfaces.CarProviderI;
import hse.kpo.interfaces.CustomerProviderI;
import java.util.Objects;

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
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar())).forEach(customer -> {
            var car = carProvider.takeCar(customer);
            if (Objects.nonNull(car)) {
                customer.setCar(car);
                log.info("found car {} for customer {}", car, customer);
            }
        });
    }
}