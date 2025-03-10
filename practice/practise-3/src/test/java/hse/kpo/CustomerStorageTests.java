package hse.kpo;

import hse.kpo.domains.Customers.Customer;
import hse.kpo.services.CustomerStorageI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Класс, тестирующий класс CustomerStorage.
 */
public class CustomerStorageTests {
    /**
     * Метод, тестирующий метод addCustomer класса CustomerStorage.
     */
    @Test
    @DisplayName("Customer Storage Tests getCustomersTest")
    public void getCustomersTest() {
        var custService = new CustomerStorageI();
        var customer = new Customer("Aboba", 0, 100, -23);

        custService.addCustomer(customer);

        Assertions.assertEquals(1, custService.getCustomers().size());
    }
}
