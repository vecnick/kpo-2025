package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PedalCarFactoryTests {

	@Autowired
	private PedalCarFactory pedalCarFactory;

	@Test
	@DisplayName("PedalCarFactory - тест создания машин на соответствие VIN номера (createCar)")
	void createdCarVinTest() {
		int carNumber = 5;
		PedalEngine engine = new PedalEngine(6);

		int car1_VIN = (new Car(carNumber, engine)).getVIN();
		int car2_VIN = pedalCarFactory.createCar(new PedalEngineParams(6), carNumber).getVIN();
		assertEquals(car1_VIN, carNumber);
		assertEquals(car2_VIN, carNumber);
	}
}