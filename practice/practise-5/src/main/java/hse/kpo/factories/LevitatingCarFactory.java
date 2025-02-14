package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.LevitatingEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика машин с левитирующим двигателем.
 */
@Component
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * Создаёт объект Car с левитирующим двигателем.
     *
     * @param carParams параметры машины
     * @param carNumber номер машины
     * @return объект класса Car
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngine();

        return new Car(carNumber, engine);
    }
}
