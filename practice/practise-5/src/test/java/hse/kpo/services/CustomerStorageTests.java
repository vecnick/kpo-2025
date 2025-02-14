package hse.kpo.services;

import hse.kpo.domains.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerStorageTests {
    @Autowired
    CustomerStorage customerStorage;

    @Test
    @DisplayName("Тест создания хранилища")
    void storageCreationTest() {
        Assertions.assertNotNull(customerStorage);
    }

    @Test
    @DisplayName("Тест добавления покупателя")
    void addCustomerTest() {
        customerStorage.addCustomer(new Customer("Test", 0, 0, 0));

        Assertions.assertEquals("Test", customerStorage.getCustomers().getLast().getName());
    }
}
