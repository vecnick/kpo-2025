package hse.kpo.services;

import hse.kpo.interfaces.cars.CarProvider;
import hse.kpo.interfaces.CustomerProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Сервис продажи машин.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCarService {

    private final CarProvider carProvider;

    private final CustomerProvider customerProvider;

    /**
     * Метод продажи машин
     */
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var car = carProvider.takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.setCar(car);
                    } else {
                        log.warn("No car in CarService");
                    }
                });
    }
}