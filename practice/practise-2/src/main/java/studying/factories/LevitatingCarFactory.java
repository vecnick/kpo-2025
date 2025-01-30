package studying.factories;

import studying.domains.Car;
import studying.domains.LevitatingEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

/**
 * Фабрика машин с левитирующим двигателем
 */
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * Создаёт объект Car с левитирующим двигателем
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
