package hse.kpo.services;

import hse.kpo.interfaces.ICarProvider;
import hse.kpo.interfaces.ICustomerProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class HseCarService {

    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    public void sellCars()
    {
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