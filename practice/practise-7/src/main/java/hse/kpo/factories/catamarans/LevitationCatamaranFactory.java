package hse.kpo.factories.catamarans;

import hse.kpo.domains.Catamaran;
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
    public Catamaran create(EmptyEngineParams catamaranParams, int catamaranNumber) {
        var engine = new LevitationEngine(); // Создаем двигатель без каких-либо параметров

        return new Catamaran(catamaranNumber, engine); // создаем катамаран с левитирующим приводом
    }
}
