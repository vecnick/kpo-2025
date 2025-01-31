package studying.factories;

import studying.domains.Car;
import studying.domains.LevitatingEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine);
    }
}
