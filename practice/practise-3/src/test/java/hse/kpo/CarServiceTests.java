package hse.kpo;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.services.CarServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


public class CarServiceTests {
    @Test
    @DisplayName("Test of Car Service Class takeCarTest")
    public void takeCarTest() {
        var carService = new CarServiceI();
        var customer = new Customer("Aboba", 0, 100, -23);

        carService.takeCar(customer);

        Assertions.assertNull(customer.getCar());
    }
}
