package hse.kpo;

import hse.kpo.factories.HandCarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class HandCarFactoryTests {

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
