package hse.kpo.domains;

import hse.kpo.domains.cars.Car;
import hse.kpo.enums.ProductionTypes;

public class CatamaranWithWheels extends Car {
    private final Catamaran catamaran;

    public CatamaranWithWheels(Catamaran catamaran) {
        super(catamaran.getVin() + 10000, catamaran.getEngine());
        this.catamaran = catamaran;
    }

    @Override
    public boolean isCompatible(Customer customer) {
        // Используем проверку совместимости для автомобилей
        return this.catamaran.getEngine().isCompatible(customer, ProductionTypes.CATAMARAN);
    }

    @Override
    public String toString() {
        return "Адаптированный катамаран VIN-" + getVin();
    }
}