package hse.kpo.factories;

import hse.kpo.interfaces.AbstractProductionFactory;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.EmptyEngineParams;

/**
 * Класс для создания транспортных средств с левитирующим двигателем
 */
public class LevitatingFactory implements AbstractProductionFactory<EmptyEngineParams> {
    /**
     * Создаёт фабрику автомобилей
     *
     * @return новая фабрика автомобилей
     */
    @Override
    public ICarFactory<EmptyEngineParams> createCarFactory() {
        return new LevitatingCarFactory();
    }

    /**
     * Создаёт фабрику катамаранов
     *
     * @return новая фабрика катамаранов
     */
    @Override
    public ICatamaranFactory<EmptyEngineParams> createCatamaranFactory() {
        return new LevitatingCatamaranFactory();
    }
}
