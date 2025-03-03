package hse.kpo;

import hse.kpo.facade.Hse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KpoApplicationTests {

	@Autowired
	private Hse hse;

	@Test
	@DisplayName("Тест загрузки контекста")
	void hseCarServiceTest() {

		hse.addCustomer("Ivan1",6,4, 150);
		hse.addCustomer("Maksim", 4, 6, 80);
		hse.addCustomer("Petya", 6, 6, 20);
		hse.addCustomer("Nikita", 4, 4, 300);

//		hse.addPedalCar(6);
//		hse.addPedalCar(6);
		hse.addWheelCatamaran();

//		hse.addHandCar();
//		hse.addHandCar();

		hse.sell();

		System.out.println(hse.generateReport());
	}

}
