package hse.kpo;

import hse.kpo.factories.Cars.HandCarFactoryI;
import hse.kpo.domains.params.EmptyEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Класс, тестирующий класс HandCarFactory.
 */
public class HandCarFactoryTests {
    /**
     * Метод, тестирующий метод createCar класса HandCarFactory.
     */
    @Test
    @DisplayName("Test of Hand Factory Class testCreateHandCar")
    public void testCreateHandCar() {
        final var fact = new HandCarFactoryI();
        final var carNumber = 100;
        final var engParams = new EmptyEngineParams();

        var handCar = fact.createCar(engParams, carNumber);

        Assertions.assertNotNull(handCar);
        Assertions.assertEquals(carNumber, handCar.getVin());
    }
}
