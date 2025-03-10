package hse.kpo.Facades;

import hse.kpo.Enums.ReportFormat;
import hse.kpo.Enums.TransportReportFormat;
import hse.kpo.Exporters.Reports.ReportExporter;
import hse.kpo.Exporters.Transport.TransportExporter;
import hse.kpo.Importers.TransportImporter.CSVTransportImporter;
import hse.kpo.Importers.TransportImporter.TransportImporter;
import hse.kpo.Observers.ReportSalesObserver;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.domains.Reports.Report;
import hse.kpo.factories.Cars.HandCarFactoryI;
import hse.kpo.factories.Cars.LevitatingCarFactoryI;
import hse.kpo.factories.Cars.PedalCarFactoryI;
import hse.kpo.factories.Exporters.ReportExporterFactory;
import hse.kpo.factories.Exporters.TransportExporterFactory;
import hse.kpo.factories.Ships.ShipFactory;
import hse.kpo.factories.WheeledShips.WheeledShipFactory;
import hse.kpo.domains.params.EmptyEngineParams;
import hse.kpo.domains.params.PedalEngineParams;
import hse.kpo.interfaces.Transport;
import hse.kpo.services.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.Writer;
import java.nio.Buffer;
import java.util.List;
import java.util.stream.Stream;

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
    private final ReportExporterFactory reportExporterFactory;
    private final TransportExporterFactory transportExporterFactory;
    private final CSVTransportImporter csvTransportImporter;
    private final TransportImporter transportImporter;

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
        hseShipService.sellCars();
    }

    public void addWheeledShip() {
        carService.addCar(wheeledShipFactory, new EmptyEngineParams());
    }

    public String generateReport() {
        return reportSalesObserver.buildReport().toString();
    }

    public void exportReport(ReportFormat format, Writer writer) {
        Report report = reportSalesObserver.buildReport();
        ReportExporter exporter = reportExporterFactory.create(format);

        try {
            exporter.export(report, writer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void importTransportFromCSV(BufferedReader reader) {
        try {
            transportImporter.importCar(carService, shipService, reader);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void exportTransport(TransportReportFormat format, Writer writer) {
        List<Transport> transports = Stream.concat(
                        carService.getCars().stream(),
                        shipService.getShips().stream())
                .toList();
        TransportExporter exporter = transportExporterFactory.create(format);

        try {
            exporter.export(transports, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}
