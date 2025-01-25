package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.PedalCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

public class Main {
    public static void main(String[] args) {
        System.out.println("HSE");

        var carService = new CarService();

        var customerStorage = new CustomerStorage();

        var hseCarService = new HseCarService(carService, customerStorage);

        var pedalCarFactory = new PedalCarFactory();

        var handCarFactory = new HandCarFactory();

        customerStorage.addCustomer(new Customer("Ivan1",6,4));
        customerStorage.addCustomer(new Customer("Maksim",4,6));
        customerStorage.addCustomer(new Customer("Petya",6,6));
        customerStorage.addCustomer(new Customer("Nikita",4,4));

        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
    }
}
