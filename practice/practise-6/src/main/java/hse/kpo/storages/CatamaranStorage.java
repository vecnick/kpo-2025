package hse.kpo.storages;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.interfaces.catamarans.CatamaranProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации о катамаранах.
 */
@Component
public class CatamaranStorage implements CatamaranProvider {

    private final List<Catamaran> catamarans = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Catamaran takeCatamaran(Customer customer) {

        var filteredCars = catamarans.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(catamarans::remove);

        return firstCar.orElse(null);
    }

    /**
     * Метод добавления {@link Catamaran} в систему.
     *
     * @param catamaranFactory фабрика для создания катамаранов
     * @param catamaranParams параметры для создания катамарана
     */
    public <ProductionParams> void addCatamaran(CatamaranFactory<ProductionParams> catamaranFactory,
                                                ProductionParams catamaranParams) {
        var car = catamaranFactory.create(
                catamaranParams,
                ++carNumberCounter
        );

        catamarans.add(car);
    }
}
