package hse.kpo.Observers;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Reports.Report;
import hse.kpo.Report.ReportBuilder;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.CreationObserver;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.services.CustomerStorageI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportSalesObserver implements SalesObserver, CreationObserver {
//    @Autowired
    private final CustomerStorageI customerStorage;

    private final ReportBuilder reportBuilder = new ReportBuilder();

    public Report buildReport() {
        return reportBuilder.build();
    }

    public void checkCustomers() {
        reportBuilder.addCustomers(customerStorage.getCustomers());
    }

    @Override
    public void onSale(Customer customer, Types productType, int vin) {
        String message;
        switch (productType) {
            case Types.CAR -> message = String.format(
                    "Продажа машины: %s VIN-%d клиенту %s (Сила рук: %d, Сила ног: %d, IQ: %d)",
                    productType, vin, customer.getName(),
                    customer.getHandPower(), customer.getLegPower(), customer.getIq()
            );
            case Types.SHIP -> message = String.format(
                    "Продажа катамарана: %s VIN-%d клиенту %s (Сила рук: %d, Сила ног: %d, IQ: %d)",
                    productType, vin, customer.getName(),
                    customer.getHandPower(), customer.getLegPower(), customer.getIq()
            );
            case null, default -> throw new IllegalArgumentException("illegal productType");
        }
        reportBuilder.addOperation(message);
    }

    @Override
    public void onCreation(Types productType, int vin) {
        String message;
        switch (productType) {
            case Types.CAR -> message = String.format(
                    "Создание машины: %s VIN-%d",
                    productType, vin
            );
            case Types.SHIP -> message = String.format(
                    "Создание катамарана: %s VIN-%d",
                    productType, vin
            );
            case null, default -> throw new IllegalArgumentException("illegal productType");
        }
        reportBuilder.addOperation(message);
    }
}