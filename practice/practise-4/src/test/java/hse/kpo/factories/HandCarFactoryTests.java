package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HandCarFactoryTests {

	@Autowired
	private HandCarFactory handCarFactory;

	@Test
	@DisplayName("HandCarFactory - тест создания машин на соответствие VIN номера (createCar)")
	void createdCarVinTest() {
		int carNumber = 5;
		HandEngine engine = new HandEngine();

		int car1_VIN = (new Car(carNumber, engine)).getVIN();
		int car2_VIN = handCarFactory.createCar(EmptyEngineParams.DEFAULT, carNumber).getVIN();
		assertEquals(car1_VIN, carNumber);
		assertEquals(car2_VIN, carNumber);
	}

//	@Test
//	@DisplayName("HandCarFactory - тест создания машин с неправильной передачей параметра двигателя")
//	void incorrectEngineParamTest() {
//		int carNumber = 5;
//		PedalEngineParams params = new PedalEngineParams(6);
//
//		assertThrows(IllegalArgumentException.class, () -> {
//			handCarFactory.createCar(params, carNumber);
//		});
//	}
}