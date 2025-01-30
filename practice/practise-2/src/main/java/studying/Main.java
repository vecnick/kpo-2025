package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.LevitatingCarFactory;
import studying.factories.PedalCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

public class Main {
    public static void main(String[] args) {
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
