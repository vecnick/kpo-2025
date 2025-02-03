package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.LevitatingEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс-фабрика летающих машин
 */
@Component
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     * Метод, создающий новую летающую машину
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса Car - новую машину
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngine();

        return new Car(carNumber, engine);
    }
}
