package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.domains.HandEngine;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CarServiceTests {
    @Autowired
    CarService carService;

    @Autowired
    LevitatingCarFactory levitatingCarFactory;

    @Test
    @DisplayName("Тест создания сервиса")
    void serviceCreationTest() {
        Assertions.assertNotNull(carService);
    }

    @Test
    @DisplayName("Тест добавления машины и её вручения (успех)")
    void addCarTest() {
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());

        Customer customer = new Customer("Test", 0, 0, 350);

        Car car = carService.takeCar(customer);

        Assertions.assertNotNull(car);
    }

    @Test
    @DisplayName("Тест добавления машины и её вручения (Провал)")
    void failTakeCarTest() {
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());

        Customer customer = new Customer("Test", 0, 0, 0);

        Car car = carService.takeCar(customer);

        Assertions.assertNotEquals(customer.getCar(), new Car(0, new HandEngine()));
    }
}
