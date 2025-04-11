package hse.kpo.facade;

import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.domains.CatamaranWithWheels;
import hse.kpo.domains.Customer;
import hse.kpo.domains.Report;
import hse.kpo.domains.cars.Car;
import hse.kpo.enums.ReportFormat;
import hse.kpo.export.transport.TransportExporter;
import hse.kpo.factories.ReportExporterFactory;
import hse.kpo.factories.TransportExporterFactory;
import hse.kpo.factories.cars.*;
import hse.kpo.factories.catamarans.*;
import hse.kpo.interfaces.Transport;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.export.reports.ReportExporter;
import hse.kpo.services.cars.HseCarService;
import hse.kpo.services.catamarans.HseCatamaranService;
import hse.kpo.storages.CustomerStorage;
import hse.kpo.observers.SalesObserver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Фасад для работы с системой продажи транспортных средств.
 * Предоставляет упрощенный интерфейс для управления клиентами,
 * транспортом и процессами продаж.
 */
@Component
@RequiredArgsConstructor
public class Hse {
    private final CustomerStorage customerStorage;
    private final HseCarService carService;
    private final HseCatamaranService catamaranService;
    private final SalesObserver salesObserver;
    private final PedalCarFactory pedalCarFactory;
    private final HandCarFactory handCarFactory;
    private final LevitationCarFactory levitationCarFactory;
    private final PedalCatamaranFactory pedalCatamaranFactory;
    private final HandCatamaranFactory handCatamaranFactory;
    private final LevitationCatamaranFactory levitationCatamaranFactory;
    private final ReportExporterFactory reportExporterFactory;
    private final TransportExporterFactory transportExporterFactory;

    @PostConstruct
    private void init() {
        carService.addObserver(salesObserver);
    }

    /**
     * Добавляет нового клиента в систему.
     *
     * @param name имя клиента
     * @param legPower сила ног (1-10)
     * @param handPower сила рук (1-10)
     * @param iq уровень интеллекта (1-200)
     * @example
     * hse.addCustomer("Анна", 7, 5, 120);
     */
    public void addCustomer(String name, int legPower, int handPower, int iq) {
        Customer customer = Customer.builder()
                .name(name)
                .legPower(legPower)
                .handPower(handPower)
                .iq(iq)
                .build();
        customerStorage.addCustomer(customer);
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        return customerStorage.updateCustomer(updatedCustomer);
    }

    public boolean deleteCustomer(String name) {
        return customerStorage.deleteCustomer(name);
    }

    /**
     * Добавляет педальный автомобиль в систему.
     *
     * @param pedalSize размер педалей (1-15)
     */
    public Car addPedalCar(int pedalSize) {
        return carService.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    /**
     * Добавляет автомобиль с ручным приводом.
     */
    public Car addHandCar() {
        return carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Добавляет левитирующий автомобиль.
     */
    public Car addLevitationCar() {
        return carService.addCar(levitationCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addWheelCatamaran() {
        carService.addExistingCar(new CatamaranWithWheels(createCatamaran()));
    }

    private Catamaran createCatamaran() {
        var engineCount = new Random().nextInt(3);

        return switch (engineCount) {
            case 0 -> catamaranService.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);
            case 1 -> catamaranService.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(6));
            case 2 -> catamaranService.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);
            default -> throw new RuntimeException("nonono");
        };
    }

    /**
     * Добавляет педальный катамаран.
     *
     * @param pedalSize размер педалей (1-15)
     */
    public Catamaran addPedalCatamaran(int pedalSize) {
        return catamaranService.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(pedalSize));
    }

    /**
     * Добавляет катамаран с ручным приводом.
     */
    public Catamaran addHandCatamaran() {
        return catamaranService.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Добавляет левитирующий катамаран.
     */
    public Catamaran addLevitationCatamaran() {
        return catamaranService.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Запускает процесс продажи доступного транспорта.
     * Автомобили продаются перед катамаранами.
     */
    public void sell() {
        carService.sellCars();
        catamaranService.sellCatamarans();
    }

    public void exportReport(ReportFormat format, Writer writer) {
        Report report = salesObserver.buildReport();
        ReportExporter exporter = reportExporterFactory.create(format);

        try {
            exporter.export(report, writer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void exportTransport(ReportFormat format, Writer writer) {
        List<Transport> transports = Stream.concat(
                carService.getCars().stream(),
                catamaranService.getCatamarans().stream())
                .toList();
        TransportExporter exporter = transportExporterFactory.create(format);

        try {
            exporter.export(transports, writer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Генерирует отчет о продажах.
     *
     * @return форматированная строка с отчетом
     * @example
     * System.out.println(hse.generateReport());
     */
    public String generateReport() {
        return salesObserver.buildReport().toString();
    }
}