package hse.kpo.facade;

import hse.kpo.builders.ReportBuilder;
import hse.kpo.domains.Customer;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.HseCarService;
import hse.kpo.storages.CarStorage;
import hse.kpo.storages.CustomerStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Hse {
    private final CarStorage carStorage;
    private final CustomerStorage customerStorage;
    private HseCarService hseCarService;

    private HandCarFactory handCarFactory = new HandCarFactory();
    private PedalCarFactory pedalCarFactory = new PedalCarFactory();
    private ReportBuilder reportBuilder = new ReportBuilder();

    public Hse(CarStorage carStorage, CustomerStorage customerStorage) {
        this.carStorage = carStorage;
        this.customerStorage = customerStorage;
        this.hseCarService = new HseCarService(carStorage, customerStorage);
    }

    public void addCustomer(String name, int legPower, int handPower, int iq) {
        Customer customer = Customer.builder().name(name).legPower(legPower).handPower(handPower).iq(iq).build();
        customerStorage.addCustomer(customer);
    }

    public void addPedalCar(int pedalSize) {
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    public void addHandCar() {
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void sell() {
        hseCarService.sellCars();
    }

    public String generateReport() {
        var report = reportBuilder
                .addOperation("Продажа автомобилей")
                .addCustomers(customerStorage.getCustomers())
                .build();
        return report.toString();
    }
}
