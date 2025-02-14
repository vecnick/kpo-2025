package hse.kpo.factories;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.LevitatingEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.EmptyEngineParams;

/**
 * Фабрика катамаранов с левитирующим двигателем.
 */
public class LevitatingCatamaranFactory implements ICatamaranFactory<EmptyEngineParams> {
    /**
     * Создаёт объект Catamaran с левитирующим двигателем.
     *
     * @param catamaranParams параметры катамарана
     * @param catamaranNumber номер катамарана
     * @return объект класса Catamaran
     */
    @Override
    public Catamaran createCatamaran(EmptyEngineParams catamaranParams, int catamaranNumber) {
        var engine = new LevitatingEngine();

        return new Catamaran(catamaranNumber, engine);
    }
}
