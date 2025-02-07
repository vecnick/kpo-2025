package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class HseCarServiceTests {
    @Autowired
    CarService carService;

    @Autowired
    CustomerStorage customerStorage;

    @Autowired
    HseCarService hseCarService;

    @Autowired
    PedalCarFactory pedalCarFactory;

    @Autowired
    HandCarFactory handCarFactory;

    @Autowired
    LevitatingCarFactory levitatingCarFactory;

    @Test
    @DisplayName("Тест создания сервиса")
    void serviceCreationTest() {
        Assertions.assertNotNull(hseCarService);
    }

    @Test
    @DisplayName("Тест продажи всех машин")
    void sellAllCars() {
        carService.addCar(handCarFactory, new EmptyEngineParams());
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());
        carService.addCar(pedalCarFactory, new PedalEngineParams(5));

        customerStorage.addCustomer(new Customer("Strong hands", 0, 10, 0));
        customerStorage.addCustomer(new Customer("Smart", 0, 0, 400));
        customerStorage.addCustomer(new Customer("Strong legs", 10, 0, 0));

        hseCarService.sellCars();
        customerStorage.getCustomers().forEach(customer -> Assertions.assertNotNull(customer.getCar()));
    }

    @Test
    @DisplayName("Тест продажи не всех машин")
    void sellNotAllCars() {
        carService.addCar(handCarFactory, new EmptyEngineParams());
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());
        carService.addCar(pedalCarFactory, new PedalEngineParams(5));

        customerStorage.addCustomer(new Customer("Strong hands", 0, 10, 0));
        customerStorage.addCustomer(new Customer("Not smart", 0, 0, 0));
        customerStorage.addCustomer(new Customer("Strong legs", 10, 0, 0));

        hseCarService.sellCars();
        var result = new ArrayList<Integer>();
        customerStorage.getCustomers().forEach(customer -> {
            var car = customer.getCar();
            if (Objects.nonNull(car)) {
                result.add(car.getVin());
            } else {
                result.add(-1);
            }
        });
        Assertions.assertEquals(List.of(1, -1, 3), result);
    }
}
