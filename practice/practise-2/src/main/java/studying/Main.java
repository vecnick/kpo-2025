package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.LevitatingCarFactory;
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
        LevitatingCarFactory levitatingCarFactory = new LevitatingCarFactory();

        Customer cust1 = new Customer("cust1_leg6_hand4_iq200", 6, 4, 200); // String name, int legPower, int handPower
        Customer cust2 = new Customer("cust2_leg4_hand6_iq250", 4, 6, 250);
        Customer cust3 = new Customer("cust3_leg6_hand6_iq300", 6, 6, 300);
        Customer cust4 = new Customer("cust4_leg4_hand4_iq350", 4, 4, 350);
        Customer cust5 = new Customer("cust4_leg4_hand4_iq100", 4, 4, 100);
        customerStorage.addCustomer(cust1);
        customerStorage.addCustomer(cust2);
        customerStorage.addCustomer(cust3);
        customerStorage.addCustomer(cust4);
        customerStorage.addCustomer(cust5);

        PedalEngineParams pedalParams1 = new PedalEngineParams(5);
        PedalEngineParams pedalParams2 = new PedalEngineParams(10);
        EmptyEngineParams handParams = new EmptyEngineParams();
        EmptyEngineParams iqParams = new EmptyEngineParams();

        carService.addCar(pedalCarFactory, pedalParams1);
        carService.addCar(pedalCarFactory, pedalParams2);
        carService.addCar(handCarFactory, handParams);
        carService.addCar(levitatingCarFactory, iqParams);
        carService.addCar(levitatingCarFactory, iqParams);

        /* Вывести на экран информацию о покупателях и их автомобилях */
        System.out.println("До продажи:");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();

        /* Вывести на экран информацию о покупателях и их автомобилях */
        System.out.println("\nПосле продажи:");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
    }
}