package hse.kpo.factories.catamarans;

import hse.kpo.domains.catamarans.Catamaran;
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
    public Catamaran create(EmptyEngineParams catamaranParams) {
        var engine = new HandEngine();

        return new Catamaran(engine);
    }
}
