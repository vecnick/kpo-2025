package hse.kpo;

import hse.kpo.Facades.Hse;
import hse.kpo.Observers.ReportSalesObserver;
import hse.kpo.Observers.Sales;
import hse.kpo.Report.ReportBuilder;
import hse.kpo.domains.Customer;
import hse.kpo.factories.HandCarFactoryI;
import hse.kpo.factories.LevitatingCarFactoryI;
import hse.kpo.factories.PedalCarFactoryI;
import hse.kpo.factories.ShipFactory;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * класс main.
 */
@SpringBootApplication
public class KpoApplication {

//    @Autowired
//    public SalesObserver salesObserver;
    /**
     * Функция main.
     *
     * @param args - аргументы командной строки.
     */
    public static void main(String[] args) {
        var context = SpringApplication.run(KpoApplication.class, args);

        final Hse hse = context.getBean(Hse.class);

        hse.addCustomer("Ivan1",6,4, 1500);
        hse.addCustomer("Maksim", 4, 6, 800);
        hse.addCustomer("Petya", 6, 6, 250);
        hse.addCustomer("Nikita", 4, 4, 300);

        hse.addPedalCar(6);
        hse.addWheeledShip();
        hse.addHandCar();

        hse.sell();

        System.out.println(hse.generateReport());
    }
}
