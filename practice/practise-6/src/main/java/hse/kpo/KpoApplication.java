package hse.kpo;

import hse.kpo.domains.Car;
import hse.kpo.domains.Catamaran;
import hse.kpo.domains.Customer;
import hse.kpo.domains.PedalEngine;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.LevitationCarFactory;
import hse.kpo.factories.catamarans.LevitationCatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.HseCarService;
import hse.kpo.services.HseCatamaranService;
import hse.kpo.storages.CarStorage;
import hse.kpo.storages.CatamaranStorage;
import hse.kpo.storages.CustomerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Точка входа в приложение.
 */
@SpringBootApplication
public class KpoApplication {

	@Autowired
	private CustomerStorage customerStorage;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KpoApplication.class, args);
		// Получаем бины из контекста

		CustomerStorage customerStorage = context.getBean(CustomerStorage.class);
		CatamaranStorage catamaranStorage = context.getBean(CatamaranStorage.class);
		CarStorage carStorage = context.getBean(CarStorage.class);
		LevitationCarFactory levitationCarFactory = context.getBean(LevitationCarFactory.class);
		LevitationCatamaranFactory levitationCatamaranFactory = context.getBean(LevitationCatamaranFactory.class);
		HseCarService hseCarService = context.getBean(HseCarService.class);
		HseCatamaranService hseCatamaranService = context.getBean(HseCatamaranService.class);

		Customer customer1 = Customer.builder().name("boris").legPower(6).handPower(6).iq(200).build();
		Customer customer2 = Customer.builder().name("ivan").legPower(7).handPower(7).iq(300).build();
		Customer customer3 = Customer.builder().name("gleb").legPower(8).handPower(8).iq(400).build();
		Customer customer4 = Customer.builder().name("egor").legPower(9).handPower(9).iq(500).build();

		customerStorage.addCustomer(customer1);
		customerStorage.addCustomer(customer2);
		customerStorage.addCustomer(customer3);
		customerStorage.addCustomer(customer4);

		carStorage.addCar(levitationCarFactory, EmptyEngineParams.DEFAULT);
		carStorage.addCar(levitationCarFactory, EmptyEngineParams.DEFAULT);
		catamaranStorage.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);
		catamaranStorage.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);

		hseCarService.sellCars();
		hseCatamaranService.sellCatamarans();

	}
}
