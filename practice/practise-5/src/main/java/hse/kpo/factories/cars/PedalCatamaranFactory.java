package hse.kpo.factories.cars;

import hse.kpo.domains.Car;
import hse.kpo.domains.Catamaran;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.PedalEngineParams;

public class PedalCatamaranFactory implements ICatamaranFactory<PedalEngineParams> {
    @Override
    public Catamaran create(PedalEngineParams catamaranParams, int catamaranNumber) {
        var engine = new PedalEngine(catamaranParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Catamaran(catamaranNumber, engine); // возвращаем собранный автомобиль с установленным двигателем и определенным номером
    }
}
