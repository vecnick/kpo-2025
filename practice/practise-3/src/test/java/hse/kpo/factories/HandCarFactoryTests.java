package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.params.EmptyEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HandCarFactoryTests {
    @Autowired
    HandCarFactory handCarFactory;

    @Test
    @DisplayName("Тест создания фабрики")
    void factoryCreationTest() {
        Assertions.assertNotNull(handCarFactory);
    }

    @Test
    @DisplayName("Тест создания машины")
    void carCreationTest() {
        Car car = handCarFactory.createCar(new EmptyEngineParams(), 0);

        Assertions.assertNotNull(car);
        Assertions.assertEquals(0, car.getVin());
    }
}
