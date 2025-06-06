package hse.kpo.adapter;

import hse.kpo.domains.Car;
import hse.kpo.domains.Catamaran;
import hse.kpo.interfaces.Engine;

public class CatamaranAsCar extends Car {

    public CatamaranAsCar(Catamaran catamaran) {
        super(catamaran.getVin(), catamaran.getEngine());
    }
}
