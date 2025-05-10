package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

@SpringBootTest
class CarServiceTests {

	// Внедрение зависимостей в объекты классов
	@Autowired
	private CarService carService;
	@Autowired
	private HandCarFactory handCarFactory;
	@Autowired
	private LevitatingCarFactory levitatingCarFactory;
	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Test
	@DisplayName("CarService - тест добавления машины (addCar)")
	void addCarTest() {
		CarService emptyCarService = new CarService();
		// Проверяет на соответствие всех полей 1-ого и 2-ого объекта
		assertThat(emptyCarService).usingRecursiveComparison().isEqualTo(carService);

		List<Car> cars =  List.of(
				new Car(1, new HandEngine()),
				new Car(2, new LevitatingEngine()),
				new Car(3, new PedalEngine(6))
		);

		carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		carService.addCar(levitatingCarFactory, EmptyEngineParams.DEFAULT);
		carService.addCar(pedalCarFactory, new PedalEngineParams(6));

		// Рекурсивная проверка приватных полей класса и полей у полей класса...
		assertThat(carService)
			.extracting("cars")
			.usingRecursiveComparison()
			.isEqualTo(cars);
		// Проверка приватного поля класса на соответствие конкретному значению
		assertThat(carService).extracting("carNumberCounter").isEqualTo(3);
	}

	@Test
	@DisplayName("CarService - тест получения подходящих машины (takeCar)")
	void takeCarNotNullTest() throws NoSuchFieldException, IllegalAccessException {
		Car car = new Car(1, new HandEngine());
		List<Car> cars = new ArrayList<>(); // Используем изменяемый список
		cars.add(car);

		// Подмена значения приватного поля
		Field vinField = CarService.class.getDeclaredField("cars");
		vinField.setAccessible(true);
		vinField.set(carService, cars);

		Customer customer = new Customer("Ivan",6,6, 200);
		Car receivedСar = carService.takeCar(customer);

		assertNotEquals(receivedСar, null);
	}

	@Test
	@DisplayName("CarService - тест получения неподходящих машины (takeCar)")
	void takeCarNullTest() throws NoSuchFieldException, IllegalAccessException {
		Car car = new Car(1, new HandEngine());
		List<Car> cars = new ArrayList<>(); // Используем изменяемый список
		cars.add(car);

		// Подмена значения приватного поля
		Field vinField = CarService.class.getDeclaredField("cars");
		vinField.setAccessible(true);
		vinField.set(carService, cars);

		Customer customer = new Customer("Ivan",1,1, 50);
		Car receivedСar = carService.takeCar(customer);

		assertEquals(receivedСar, null);
	}
}