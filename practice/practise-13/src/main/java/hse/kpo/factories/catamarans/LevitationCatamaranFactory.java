package hse.kpo.factories.catamarans;

import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link LevitationEngine} типом двигателя.
 */
@Component
public class LevitationCatamaranFactory implements CatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran create(EmptyEngineParams catamaranParams) {
        var engine = new LevitationEngine();

        return new Catamaran(engine);
    }
}
