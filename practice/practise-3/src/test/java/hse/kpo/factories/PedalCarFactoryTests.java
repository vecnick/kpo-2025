package hse.kpo.factories;

import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PedalCarFactoryTests {
    @Autowired
    PedalCarFactory pedalCarFactory;

    @Test
    @DisplayName("Тест создания фабрики")
    void factoryCreationTest() {
        Assertions.assertNotNull(pedalCarFactory);
    }

    @Test
    @DisplayName("Тест создания машины")
    void carCreationTest() {
        var car = pedalCarFactory.createCar(new PedalEngineParams(5), 0);

        Assertions.assertNotNull(car);
        Assertions.assertEquals(0, car.getVin());
    }
}
