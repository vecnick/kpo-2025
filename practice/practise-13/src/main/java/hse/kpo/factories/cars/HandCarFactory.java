package hse.kpo.factories.cars;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.cars.CarFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания машин с {@link HandEngine} типом двигателя.
 */
@Component
public class HandCarFactory implements CarFactory<EmptyEngineParams> {
    @Override
    public Car create(EmptyEngineParams carParams) {
        var engine = new HandEngine();

        return new Car(engine);
    }
}
