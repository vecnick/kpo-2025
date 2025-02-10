package hse.kpo;

import hse.kpo.factories.LevitatingCarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Класс, тестирующий класс LevitatingCarFactory.
 */
@SpringBootTest
public class LevitatingCarFactoryTests {
    @Autowired private LevitatingCarFactoryI fact;

    /**
     * Метод, тестирующий метод createCar класса LevitatingCarFactory.
     */
    @Test
    @DisplayName("Test of Levitating factory Class testCreateLevitatingCar")
    public void testCreateLevitatingCar() {
        final var carNumber = 100;
        final var engParams = new EmptyEngineParams();

        var handCar = fact.createCar(engParams, carNumber);

        Assertions.assertNotEquals(handCar, fact);
        Assertions.assertNotNull(handCar);
        Assertions.assertEquals(carNumber, handCar.getVin());
    }
}
