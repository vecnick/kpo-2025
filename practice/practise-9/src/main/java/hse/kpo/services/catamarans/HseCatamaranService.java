package hse.kpo.services.catamarans;

import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.interfaces.catamarans.CatamaranProvider;
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

    private final CatamaranProvider catamaranProvider;

    private final CustomerProvider customerProvider;

    /**
     * Метод продажи катамаранов.
     */
    public void sellCatamarans() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCatamaran()))
                .forEach(customer -> {
                    var catamaran = catamaranProvider.takeCatamaran(customer);
                    if (Objects.nonNull(catamaran)) {
                        customer.setCatamaran(catamaran);
                    } else {
                        log.warn("No catamaran in CatamaranService");
                    }
                });
    }
}