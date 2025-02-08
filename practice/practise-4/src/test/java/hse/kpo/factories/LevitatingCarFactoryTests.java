package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LevitatingCarFactoryTests {

	@Autowired
	private LevitatingCarFactory levitatingCarFactory;

	@Test
	@DisplayName("LevitatingCarFactory - тест создания машин на соответствие VIN номера (createCar)")
	void createdCarVinTest() {
		int carNumber = 5;
		HandEngine engine = new HandEngine();

		int car1_VIN = (new Car(carNumber, engine)).getVIN();
		int car2_VIN = levitatingCarFactory.createCar(EmptyEngineParams.DEFAULT, carNumber).getVIN();
		assertEquals(car1_VIN, carNumber);
		assertEquals(car2_VIN, carNumber);
	}
}