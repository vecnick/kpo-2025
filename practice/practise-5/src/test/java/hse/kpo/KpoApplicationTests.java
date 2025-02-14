package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarStorage;
import hse.kpo.services.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

	@Autowired
	private CarStorage carStorage;

	@Autowired
	private CustomerStorage customerStorage;

	@Autowired
	private HseCarService hseCarService;

	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Autowired
	private HandCarFactory handCarFactory;

	@Test
	@DisplayName("Тест загрузки контекста")
	void contextLoads() {
		Assertions.assertNotNull(carStorage);
		Assertions.assertNotNull(customerStorage);
		Assertions.assertNotNull(hseCarService);
	}

	@Test
	@DisplayName("Тест загрузки контекста")
	void hseCarServiceTest() {
		customerStorage.addCustomer(new Customer("Ivan1",6,4, 80));
		customerStorage.addCustomer(new Customer("Maksim",4,6, 50));
		customerStorage.addCustomer(new Customer("Petya",6,6, 120));
		customerStorage.addCustomer(new Customer("Nikita",4,4, 100));

		carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));
		carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));

		carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

		hseCarService.sellCars();

		customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
	}

}
