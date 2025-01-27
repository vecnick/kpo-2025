package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.PedalCarFactory;
import studying.factories.FlyingCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

public class Main {
    public static void main(String[] args) {
        System.out.println("HSE");

        // 1
        var carService = new CarService();

        // 2
        var customerStorage = new CustomerStorage();

        // 3
        var hseCarService = new HseCarService(carService, customerStorage);

        // 4
        var pedalCarFactory = new PedalCarFactory();

        // 5
        var handCarFactory = new HandCarFactory();

        // 6

        customerStorage.addCustomer(new Customer("Вася", 6, 4));
        customerStorage.addCustomer(new Customer("Вова", 4, 6));
        customerStorage.addCustomer(new Customer("Света", 6, 6));
        customerStorage.addCustomer(new Customer("Петя", 4, 4));

        var flyingCarFactory = new FlyingCarFactory();
        customerStorage.addCustomer(new Customer("Smart", 1, 1, 301));

        // 7

        carService.addCar(pedalCarFactory, new PedalEngineParams(2));
        carService.addCar(pedalCarFactory, new PedalEngineParams(2));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, new EmptyEngineParams());

        carService.addCar(flyingCarFactory, EmptyEngineParams.DEFAULT);

        // 8

        for (var customer : customerStorage.getCustomers()) {
            System.out.println("Customer: " + customer.getName() + ", car: " + (customer.getCar() != null ? customer.getCar().toString() : "null"));
        }

        // 9

        hseCarService.sellCars();

        // 10

        System.out.println("------------------------------------");

        for (var customer : customerStorage.getCustomers()) {
            var car = customer.getCar();
            System.out.println("Customer: " + customer.getName() + ", car: " + (car != null ? car.toString() : "null"));
        }

    }
}
