package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.PedalCarFactory;
import studying.interfaces.ICarProvider;
import studying.interfaces.ICustomerProvider;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

public class Main {
    public static void main(String[] args) {
        System.out.println("HSE");

        CarService carService = new CarService(); // ICarProvider
        CustomerStorage customerStorage = new CustomerStorage(); // ICustomerProvider
        HseCarService hseCarService = new HseCarService(carService, customerStorage);
        PedalCarFactory pedalCarFactory = new PedalCarFactory();
        HandCarFactory handCarFactory = new HandCarFactory();

        Customer cust1 = new Customer("cust1_leg6_hand4", 6, 4); // String name, int legPower, int handPower
        Customer cust2 = new Customer("cust2_leg4_hand6", 4, 6);
        Customer cust3 = new Customer("cust3_leg6_hand6", 6, 6);
        Customer cust4 = new Customer("cust4_leg4_hand4", 4, 4);
        customerStorage.addCustomer(cust1);
        customerStorage.addCustomer(cust2);
        customerStorage.addCustomer(cust3);
        customerStorage.addCustomer(cust4);

        PedalEngineParams pedalParams1 = new PedalEngineParams(5);
        PedalEngineParams pedalParams2 = new PedalEngineParams(10);
        EmptyEngineParams handParams = new EmptyEngineParams();

        carService.addCar(pedalCarFactory, pedalParams1);
        carService.addCar(pedalCarFactory, pedalParams2);
        carService.addCar(handCarFactory, handParams);
        carService.addCar(handCarFactory, handParams);

        /* Вывести на экран информацию о покупателях и их автомобилях */
        System.out.println(customerStorage.getCustomers());

        hseCarService.sellCars();

        /* Вывести на экран информацию о покупателях и их автомобилях */
        System.out.println(customerStorage.getCustomers());
    }
}