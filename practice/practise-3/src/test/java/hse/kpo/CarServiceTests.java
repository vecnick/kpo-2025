package hse.kpo;

import hse.kpo.domains.Customers.Customer;
import hse.kpo.services.CarServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Класс, тестирующий класс CarService.
 */
public class CarServiceTests {
    /**
     * Метод, тестирующий метод takeCar класса CarService.
     */
    @Test
    @DisplayName("Test of Car Service Class takeCarTest")
    public void takeCarTest() {
        var carService = new CarServiceI();
        var customer = new Customer("Aboba", 0, 100, -23);

        carService.takeCar(customer);

        Assertions.assertNull(customer.getCar());
    }
}
