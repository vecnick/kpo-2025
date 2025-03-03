package hse.kpo.factories.catamarans;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link HandEngine} типом двигателя.
 */
@Component
public class HandCatamaranFactory implements CatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran create(EmptyEngineParams catamaranParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Catamaran(carNumber, engine); // создаем катамаран с ручным приводом
    }
}
