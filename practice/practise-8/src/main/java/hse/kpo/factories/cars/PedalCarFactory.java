package hse.kpo.factories.cars;


import hse.kpo.domains.cars.Car;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.cars.CarFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания машин с {@link PedalEngine} типом двигателя.
 */
@Component
public class PedalCarFactory implements CarFactory<PedalEngineParams> {
    @Override
    public Car create(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(carNumber, engine);
    }
}
