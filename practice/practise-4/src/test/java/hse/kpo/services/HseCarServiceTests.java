package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.services.CarService;
import hse.kpo.services.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class HseCarServiceTests {

	// Внедрение зависимостей в объекты классов
	@Autowired
	private CustomerStorage customerStorage;
	@Autowired
	private HseCarService hseCarService;
	@Autowired
	private CarService carService;

	@Test
	@DisplayName("HseCarService - тест с проверкой продажи подходящих машин (sellCars)")
	void sellCarsCompatibleTest() throws NoSuchFieldException, IllegalAccessException {
		List<Car> cars = new ArrayList<>(List.of(
				new Car(1, new HandEngine()),
				new Car(2, new PedalEngine(6))
		));

		List<Customer> customers = new ArrayList<>(List.of(
				new Customer("Ivan", 6, 6, 200),
				new Customer("Boris", 6, 6, 200)
		));

		// Подмена значения приватного поля
		Field vinField1 = CarService.class.getDeclaredField("cars");
		vinField1.setAccessible(true);
		vinField1.set(carService, cars);

		// Подмена значения приватного поля
		Field vinField2 = CustomerStorage.class.getDeclaredField("customers");
		vinField2.setAccessible(true);
		vinField2.set(customerStorage, customers);

		List<Customer> answerCustomers = new ArrayList<>(List.of(
				new Customer("Ivan", 6, 6, 200),
				new Customer("Boris", 6, 6, 200)
		));
		answerCustomers.get(0).setCar(new Car(1, new HandEngine()));
		answerCustomers.get(1).setCar(new Car(2, new PedalEngine(6)));

		hseCarService.sellCars();

		// Проверяет на соответствие всех полей 1-ого и 2-ого объекта
		assertThat(customerStorage.getCustomers()).usingRecursiveComparison().isEqualTo(answerCustomers);
	}

	@Test
	@DisplayName("HseCarService - тест с проверкой продажи неподходящих машин (sellCars)")
	void sellCarsNotCompatibleTest() throws NoSuchFieldException, IllegalAccessException {
		List<Car> cars = new ArrayList<>(List.of(
				new Car(1, new HandEngine()),
				new Car(2, new PedalEngine(6))
		));

		List<Customer> customers = new ArrayList<>(List.of(
				new Customer("Ivan", 1, 1, 50),
				new Customer("Boris", 1, 1, 50)
		));

		// Подмена значения приватного поля
		Field vinField1 = CarService.class.getDeclaredField("cars");
		vinField1.setAccessible(true);
		vinField1.set(carService, cars);

		// Подмена значения приватного поля
		Field vinField2 = CustomerStorage.class.getDeclaredField("customers");
		vinField2.setAccessible(true);
		vinField2.set(customerStorage, customers);

		List<Customer> answerCustomers = new ArrayList<>(List.of(
				new Customer("Ivan", 1, 1, 50),
				new Customer("Boris", 1, 1, 50)
		));
		answerCustomers.get(0).setCar(null);
		answerCustomers.get(1).setCar(null);

		hseCarService.sellCars();

		// Проверяет на соответствие всех полей 1-ого и 2-ого объекта
		assertThat(customerStorage.getCustomers()).usingRecursiveComparison().isEqualTo(answerCustomers);
	}
}