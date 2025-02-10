package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactoryI;
import hse.kpo.factories.LevitatingCarFactoryI;
import hse.kpo.factories.PedalCarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarServiceI;
import hse.kpo.services.CustomerStorageI;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Класс, тестирующий приложение Spring.
 */
@SpringBootTest
class KpoApplicationTests {
    @Autowired
    private CarServiceI carService;
    @Autowired
    private CustomerStorageI customerStorage;
    @Autowired
    private HseCarService hseCarService;
    @Autowired
    private PedalCarFactoryI pedalCarFactory;
    @Autowired
    private HandCarFactoryI handCarFactory;
    @Autowired
    private LevitatingCarFactoryI levitatingCarFactory;

    /**
     * Тест загрузки контекста.
     */
    @Test
    @DisplayName("Тест загрузки контекста")
    void contextLoads() {
        customerStorage.addCustomer(new Customer("Alisa", 6, 4, 98));
        customerStorage.addCustomer(new Customer("Bob", 4, 6, 102));
        customerStorage.addCustomer(new Customer("Chris", 6, 6, 200));
        customerStorage.addCustomer(new Customer("Daemon", 4, 4, 340));
        customerStorage.addCustomer(new Customer("Eva", 1, 2, 500));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
        hseCarService.sellCars();
        System.out.println("Sold out");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
    }
}
