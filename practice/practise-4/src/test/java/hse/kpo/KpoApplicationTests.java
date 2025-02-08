package hse.kpo;

import hse.kpo.domains.*;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class KpoApplicationTests {

	// Внедрение зависимостей в объекты классов
	@Autowired
	private CustomerStorage customerStorage;
	@Autowired
	private HseCarService hseCarService;
	@Autowired
	private CarService carService;
	@Autowired
	private HandCarFactory handCarFactory;
	@Autowired
	private LevitatingCarFactory levitatingCarFactory;
	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Test
	@DisplayName("CarService (с применением Mock) - тест добавления и получения машины (addCar, takeCar)")
	void test1() {
		Customer customer1 = new Customer("Ivan",4,4, 100);
		Customer customer2 = new Customer("Ivan",6,6, 200);
		carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		assertEquals(carService.takeCar(customer1), null); // проверить что машина не подошла
		assertNotEquals(carService.takeCar(customer2), null); // проверить что машина подошла

	}

	@Test
	@DisplayName("CustomerStorage (с применением Spy) - тест добавления и вывода пользователей (addCustomer, getCustomers)")
	void test2() {
		Customer customer1 = new Customer("Ivan",6,4, 200);
		Customer customer2 = new Customer("Boris",15,20, 1000);
		List<Customer> customers = List.of(customer1, customer2);

		customerStorage.addCustomer(customer1);
		customerStorage.addCustomer(customer2);

		assertEquals(customerStorage.getCustomers(), customers);
	}

	@Test
	@DisplayName("HseCarService - тест продажи машин (sellCars)")
	void test3() {
		Customer customer1 = new Customer("Ivan1",6,4, 200);
		Customer customer2 = new Customer("Ivan1",6,4, 200);
		List<Customer> customers = List.of(customer1, customer2);

		customerStorage.addCustomer(customer1);
		customerStorage.addCustomer(customer2);
		carService.addCar(pedalCarFactory, new PedalEngineParams(6));
		carService.addCar(pedalCarFactory, new PedalEngineParams(6));

		hseCarService.sellCars();
		assertEquals(customerStorage.getCustomers(), customers);
	}

	@Test
	@DisplayName("HandCarFactory - тест создания машин (createCar)")
	void test4() {
		int carNumber = 5;
		HandEngine engine = new HandEngine();

		int car1_VIN = (new Car(carNumber, engine)).getVIN();
		int car2_VIN = handCarFactory.createCar(EmptyEngineParams.DEFAULT, carNumber).getVIN();
		assertEquals(car1_VIN, car2_VIN);
		assertEquals(car1_VIN, carNumber);
	}

	@Test
	@DisplayName("LevitatingCarFactory - тест создания машин (createCar)")
	void test5() {
		int carNumber = 5;
		LevitatingEngine engine = new LevitatingEngine();

		int car1_VIN = (new Car(carNumber, engine)).getVIN();
		int car2_VIN = levitatingCarFactory.createCar(EmptyEngineParams.DEFAULT, carNumber).getVIN();
		assertEquals(car1_VIN, car2_VIN);
		assertEquals(car1_VIN, carNumber);
	}

	@Test
	@DisplayName("PedalCarFactory - тест создания машин (createCar)")
	void test6() {
		int carNumber = 5;
		PedalEngine engine = new PedalEngine(6);

		int car1_VIN = (new Car(carNumber, engine)).getVIN();
		int car2_VIN = pedalCarFactory.createCar(new PedalEngineParams(6), carNumber).getVIN();
		assertEquals(car1_VIN, car2_VIN);
		assertEquals(car1_VIN, carNumber);
	}
}