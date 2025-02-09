package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.LevitatingEngineI;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс-фабрика летающих машин.
 */
@Component
public class LevitatingCarFactoryI implements CarFactoryI<EmptyEngineParams> {

    /**
     * Метод, создающий новую летающую машину.
     *
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса Car - новую машину
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngineI();

        return new Car(carNumber, engine);
    }
}
