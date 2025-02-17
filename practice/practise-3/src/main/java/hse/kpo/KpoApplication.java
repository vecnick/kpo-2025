package hse.kpo;

import hse.kpo.Report.ReportBuilder;
import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactoryI;
import hse.kpo.factories.LevitatingCarFactoryI;
import hse.kpo.factories.PedalCarFactoryI;
import hse.kpo.factories.ShipFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * класс main.
 */
@SpringBootApplication
public class KpoApplication {
    /**
     * Функция main.
     *
     * @param args - аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(KpoApplication.class, args);


        var customerStorage = new CustomerStorageI();
        customerStorage.addCustomer(new Customer("Alisa", 6, 4, 98));
        customerStorage.addCustomer(new Customer("Bob", 4, 6, 102));
        customerStorage.addCustomer(new Customer("Chris", 6, 6, 200));
        customerStorage.addCustomer(new Customer("Daemon", 4, 4, 340));
        customerStorage.addCustomer(new Customer("Eva", 1, 2, 500));

        var carService = new CarServiceI();
        var pedalCarFactory = new PedalCarFactoryI();
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));

        var shipService = new ShipService();
        var shipFactory = new ShipFactory();
        shipService.addCar(shipFactory, new EmptyEngineParams());
        shipService.addCar(shipFactory, new EmptyEngineParams());

        var handCarFactory = new HandCarFactoryI();
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        var levitatingCarFactory = new LevitatingCarFactoryI();
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());
        carService.addCar(levitatingCarFactory, new EmptyEngineParams());

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        var hseCarService = new HseCarService(carService, customerStorage);

        var reportBuilder = new ReportBuilder()
                .addOperation("Инициализация системы")
                .addCustomers(customerStorage.getCustomers());

        hseCarService.sellCars();

        var report = reportBuilder
                .addOperation("Продажа автомобилей")
                .addCustomers(customerStorage.getCustomers())
                .build();

        System.out.println(report.toString());

        System.out.println("Sold out");

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        var hseShipService = new HseShipService(shipService, customerStorage);

        reportBuilder = new ReportBuilder()
                .addOperation("Инициализация системы")
                .addCustomers(customerStorage.getCustomers());

        hseShipService.sellCars();

        report = reportBuilder
                .addOperation("Продажа катамаранов")
                .addCustomers(customerStorage.getCustomers())
                .addCars(carService.getCars())
                .addShips(shipService.getShips())
                .build();

        System.out.println(report.toString());
    }
}
