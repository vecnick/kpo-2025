package hse.kpo.factories.Cars;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Engines.LevitatingEngineI;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.domains.params.EmptyEngineParams;
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
     * @return экземпляр класса Car - новую машину
     */
    @Override
    public Car createCar(EmptyEngineParams carParams) {
        var engine = new LevitatingEngineI();

        return new Car(engine);
    }
}
