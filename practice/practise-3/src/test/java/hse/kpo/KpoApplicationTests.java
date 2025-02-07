package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarService;
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
	CarService carService;

	@Autowired
	CustomerStorage customerStorage;

	@Autowired
	HseCarService hseCarService;

	@Autowired
	PedalCarFactory pedalCarFactory;

	@Autowired
	HandCarFactory handCarFactory;

	@Autowired
	LevitatingCarFactory levitatingCarFactory;

	@Test
	@DisplayName("Тест загрузки контекста")
	void contextLoads() {
		Assertions.assertNotNull(carService);
		Assertions.assertNotNull(customerStorage);
		Assertions.assertNotNull(hseCarService);
		Assertions.assertNotNull(pedalCarFactory);
		Assertions.assertNotNull(handCarFactory);
		Assertions.assertNotNull(levitatingCarFactory);
	}

	@Test
	@DisplayName("Тест Автосервиса")
	void hseCarServiceTest() {
		customerStorage.addCustomer(new Customer("John", 6, 4, 110));
		customerStorage.addCustomer(new Customer("Bob", 4, 6, 120));
		customerStorage.addCustomer(new Customer("Jim", 6, 6, 105));
		customerStorage.addCustomer(new Customer("Dick", 4, 4, 60));
		customerStorage.addCustomer(new Customer("Genius", 5, 5, 310));

		carService.addCar(pedalCarFactory, new PedalEngineParams(5));
		carService.addCar(pedalCarFactory, new PedalEngineParams(5));
		carService.addCar(handCarFactory, new EmptyEngineParams());
		carService.addCar(handCarFactory, new EmptyEngineParams());
		carService.addCar(levitatingCarFactory, new EmptyEngineParams());

		System.out.println("=Before Selling=");
		System.out.println(customerStorage.getCustomers().toString());

		hseCarService.sellCars();

		System.out.println("=After Selling=");
		System.out.println(customerStorage.getCustomers().toString());
	}
}