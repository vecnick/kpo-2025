package hse.kpo.factories;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.EmptyEngineParams;

/**
 * Фабрика катамаранов с ручным двигателем.
 */
public class HandCatamaranFactory implements ICatamaranFactory<EmptyEngineParams> {
    /**
     * Создаёт объект Catamaran с ручным двигателем.
     *
     * @param catamaranParams параметры катамарана
     * @param catamaranNumber номер катамарана
     * @return объект класса Catamaran
     */
    @Override
    public Catamaran createCatamaran(EmptyEngineParams catamaranParams, int catamaranNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Catamaran(catamaranNumber, engine); // создаем автомобиль с ручным приводом
    }
}
