package hse.kpo.Facades;

import hse.kpo.KpoApplication;
import hse.kpo.Observers.ReportSalesObserver;
import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.factories.*;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor()
public class Hse {
    private final ShipFactory shipFactory;
    private final HandCarFactoryI handCarFactory;
    private final LevitatingCarFactoryI levitatingCarFactory;
    private final PedalCarFactoryI pedalCarFactory;
    private final CustomerStorageI customerStorage;
    private final ShipService shipService;
    private final WheeledShipFactory wheeledShipFactory;
    private final CarServiceI carService;
    private final HseCarService hseCarService;
    private final HseShipService hseShipService;
    private final ReportSalesObserver reportSalesObserver;

    @PostConstruct
    private void setUp() {
        hseCarService.addObserver(reportSalesObserver);
        hseShipService.addObserver(reportSalesObserver);
        carService.addObserver(reportSalesObserver);
    }

    public void addCustomer(String name, int legPower, int handPower, int iq) {
        customerStorage.addCustomer(new Customer(name, legPower, handPower, iq));
    }

    public void addPedalCar(int pedalSize) {
        carService.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    public void addHandCar() {
        carService.addCar(handCarFactory, new EmptyEngineParams());
    }

    public void sell() {
        hseCarService.sellCars();
    }

    public void addWheeledShip() {
        carService.addCar(wheeledShipFactory, new EmptyEngineParams());
    }

    public String generateReport() {
        return reportSalesObserver.buildReport().toString();
    }
}
