package studying;

import studying.domains.Customer;
import studying.domains.PedalEngine;
import studying.factories.HandCarFactory;
import studying.factories.PedalCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

public class Main {
    public static void main(String[] args) {
        var carService = new CarService();
        var customerStorage = new CustomerStorage();
        var hseCarService = new HseCarService(carService, customerStorage);
        var pedalCarFactory = new PedalCarFactory();
        var handCarFactory = new HandCarFactory();

        var Agil = new Customer("Agil", 6, 4);
        var Roma = new Customer("Roma", 4, 6);
        var Kirill = new Customer("Kirill", 4, 4);
        var Kostya = new Customer("Kostya", 6, 6);

        customerStorage.addCustomer(Agil);
        customerStorage.addCustomer(Roma);
        customerStorage.addCustomer(Kirill);
        customerStorage.addCustomer(Kostya);

        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(pedalCarFactory, new PedalEngineParams(4));

        carService.addCar(handCarFactory, new EmptyEngineParams());
        carService.addCar(handCarFactory, new EmptyEngineParams());

        System.out.println(customerStorage.getCustomers());

        hseCarService.sellCars();

        System.out.println(customerStorage.getCustomers());


        System.out.println("HSE");
    }
}
