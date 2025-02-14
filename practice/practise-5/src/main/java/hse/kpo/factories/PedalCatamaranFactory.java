package hse.kpo.factories;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.PedalEngineParams;

/**
 * Фабрика машин с педальным двигателем.
 */
public class PedalCatamaranFactory implements ICatamaranFactory<PedalEngineParams> {
    /**
     * Создаёт объект Car с педальным двигателем.
     *
     * @param catamaranParams параметры катамарана
     * @param catamaranNumber номер катамарана
     * @return объект класса Catamaran
     */
    @Override
    public Catamaran createCatamaran(PedalEngineParams catamaranParams, int catamaranNumber) {
        var engine = new PedalEngine(catamaranParams.pedalSize());

        return new Catamaran(catamaranNumber, engine);
    }
}
