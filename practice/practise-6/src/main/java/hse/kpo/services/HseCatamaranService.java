package hse.kpo.services;

import hse.kpo.domains.Customer;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.interfaces.Sales;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.interfaces.catamarans.CatamaranProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Сервис продажи катамаранов.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCatamaranService {
    final List<SalesObserver> observers = new ArrayList<>();

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, ProductionTypes productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }

    private final CatamaranProvider catamaranProvider;

    private final CustomerProvider customerProvider;

    /**
     * Метод продажи катамаранов.
     */
    @Sales("Продажа катамаранов")
    public void sellCatamarans() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCatamaran()))
                .forEach(customer -> {
                    var catamaran = catamaranProvider.takeCatamaran(customer);
                    if (Objects.nonNull(catamaran)) {
                        customer.setCatamaran(catamaran);
                        notifyObserversForSale(customer, ProductionTypes.CATAMARAN, catamaran.getVin());
                    } else {
                        log.warn("No catamaran in CatamaranService");
                    }
                });
    }
}