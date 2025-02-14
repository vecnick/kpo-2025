package hse.kpo.factories;

import hse.kpo.interfaces.AbstractProductionFactory;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.PedalEngineParams;

/**
 * Класс для создания транспортных средств с педальным двигателем
 */
public class PedalFactory implements AbstractProductionFactory<PedalEngineParams> {
    /**
     * Создаёт фабрику автомобилей
     *
     * @return новая фабрика автомобилей
     */
    @Override
    public ICarFactory<PedalEngineParams> createCarFactory() {
        return new PedalCarFactory();
    }

    /**
     * Создаёт фабрику катамаранов
     *
     * @return новая фабрика катамаранов
     */
    @Override
    public ICatamaranFactory<PedalEngineParams> createCatamaranFactory() {
        return new PedalCatamaranFactory();
    }
}
