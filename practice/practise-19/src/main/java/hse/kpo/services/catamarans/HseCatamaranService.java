package hse.kpo.services.catamarans;

import hse.kpo.domains.Customer;
import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.interfaces.catamarans.CatamaranProvider;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import hse.kpo.repositories.CatamaranRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Сервис продажи катамаранов.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCatamaranService implements CatamaranProvider{

    private final CustomerProvider customerProvider;
    private final CatamaranRepository catamaranRepository;

    /**
     * Метод продажи катамаранов.
     */
    public void sellCatamarans() {
        var customers = customerProvider.getCustomers();
        customers.stream().filter(customer -> Objects.isNull(customer.getCatamaran()))
            .forEach(customer -> {
                var catamaran = this.takeCatamaran(customer);
                if (Objects.nonNull(catamaran)) {
                    customer.setCatamaran(catamaran);
                } else {
                    log.warn("No catamaran in CatamaranService");
                }
            });
    }

    @Override
    public Catamaran takeCatamaran(Customer customer) {

        var filteredCatamarans = catamaranRepository.findAll().stream().filter(Catamaran -> Catamaran.isCompatible(customer)).toList();

        var firstCatamaran = filteredCatamarans.stream().findFirst();

        firstCatamaran.ifPresent(catamaranRepository::delete);

        return firstCatamaran.orElse(null);
    }

    /**
     * Метод добавления {@link Catamaran} в систему.
     *
     * @param catamaranFactory фабрика для создания автомобилей
     * @param catamaranParams параметры для создания автомобиля
     */
    public <T> Catamaran addCatamaran(CatamaranFactory<T> catamaranFactory, T catamaranParams) {
        return catamaranRepository.save(catamaranFactory.create(catamaranParams));
    }

    public Catamaran addExistingCatamaran(Catamaran Catamaran) {
        return catamaranRepository.save(Catamaran);
    }

    public Optional<Catamaran> findByVin(Integer vin) {
        return catamaranRepository.findById(vin);
    }

    public void deleteByVin(Integer vin) {
        catamaranRepository.deleteById(vin);
    }

    public List<Catamaran> getCatamaransWithFiltration(String engineType, Integer vin) {
        return catamaranRepository.findCatamaransByEngineTypeAndVinGreaterThan(engineType, vin);
    }

    public List<Catamaran> getCatamarans() {
        return catamaranRepository.findAll();
    }
}