package hse.kpo;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.domains.PedalEngineI;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.services.CarServiceI;
import hse.kpo.services.CustomerStorageI;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;


public class HSECarServiceTest {
    @Test
    @DisplayName("HSE Car service test getCustomersTest")
    public void getCustomersTest() {
        var custStor = Mockito.mock(CustomerStorageI.class);
        var carServ = Mockito.mock(CarServiceI.class);

        ArrayList<Customer> data = new ArrayList<>();
        data.add(new Customer("aboba", 1, 1, 1));
        Mockito.when(custStor.getCustomers()).thenReturn(data);

        var hseCarServ = new HseCarService(carServ, custStor);

        hseCarServ.sellCars();

    }

    @Test
    @DisplayName("HSE Car service test getCustomersTest2")
    public void getCustomersTest2() {
        var custStor = Mockito.spy(CustomerStorageI.class);
        var carServ = Mockito.spy(CarServiceI.class);

        ArrayList<Customer> data = new ArrayList<>();
        data.add(new Customer("aboba", 1, 1, 1));
        Mockito.when(custStor.getCustomers()).thenReturn(data);

        var hseCarServ = new HseCarService(carServ, custStor);

        hseCarServ.sellCars();
    }

}
