package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactory;
import hse.kpo.factories.PedalCarFactory;
import hse.kpo.factories.LevitatingCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.CarService;
import hse.kpo.services.CustomerStorage;
import hse.kpo.services.HseCarService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KpoApplication.class, args);

		CarService carService = new CarService();
		CustomerStorage customerStorage = new CustomerStorage();
		HseCarService hseCarService = new HseCarService(carService, customerStorage);
		PedalCarFactory pedalCarFactory = new PedalCarFactory();
		HandCarFactory handCarFactory = new HandCarFactory();
		LevitatingCarFactory levitatingCarFactory = new LevitatingCarFactory();

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
