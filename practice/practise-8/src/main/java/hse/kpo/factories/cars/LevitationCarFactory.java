package hse.kpo.factories.cars;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.interfaces.cars.CarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания машин с {@link LevitationEngine} типом двигателя.
 */
@Component
public class LevitationCarFactory implements CarFactory<EmptyEngineParams> {
    @Override
    public Car create(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitationEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с левитирующим приводом
    }
}
