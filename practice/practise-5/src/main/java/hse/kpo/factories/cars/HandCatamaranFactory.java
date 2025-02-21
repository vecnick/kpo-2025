package hse.kpo.factories.cars;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

@Component
public class HandCatamaranFactory implements ICatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran create(EmptyEngineParams catamaranParams, int catamaranNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Catamaran(catamaranNumber, engine); // создаем автомобиль с ручным приводом
    }
}
