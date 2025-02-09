package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.services.CarServiceI;
import hse.kpo.services.CustomerStorageI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class CustomerStorageTests {

    @Test
    @DisplayName("Customer Storage Tests getCustomersTest")
    public void getCustomersTest() {
        var custService = new CustomerStorageI();
        var customer = new Customer("Aboba", 0, 100, -23);

        custService.addCustomer(customer);

        Assertions.assertEquals(1, custService.getCustomers().size() );
    }
}
