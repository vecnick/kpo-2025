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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KpoApplication {
	public static void main(String[] args) {
		SpringApplication.run(KpoApplication.class, args);

		var carService = new CarService();

		var customerStorage = new CustomerStorage();

		var hseCarService = new HseCarService(carService, customerStorage);

		var pedalCarFactory = new PedalCarFactory();

		var handCarFactory = new HandCarFactory();

		var levitatingCarFactory = new LevitatingCarFactory();

		customerStorage.addCustomer(new Customer("Alisa", 6, 4, 98));
		customerStorage.addCustomer(new Customer("Bob", 4, 6, 102));
		customerStorage.addCustomer(new Customer("Chris", 6, 6, 200));
		customerStorage.addCustomer(new Customer("Daemon", 4, 4, 340));
		customerStorage.addCustomer(new Customer("Eva", 1, 2, 500));

		carService.addCar(pedalCarFactory, new PedalEngineParams(6));
		carService.addCar(pedalCarFactory, new PedalEngineParams(6));

		carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
		carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

		carService.addCar(levitatingCarFactory, new EmptyEngineParams());
		carService.addCar(levitatingCarFactory, new EmptyEngineParams());

		customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

		hseCarService.sellCars();
		System.out.println("Sold out");

		customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
	}
}
