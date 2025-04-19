package hse.kpo.factories.Cars;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Engines.HandEngineI;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.domains.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс, производящий машины с ручным двигателем.
 */
@Component
public class HandCarFactoryI implements CarFactoryI<EmptyEngineParams> {
    /**
     * Метод, создающий машину.
     *
     * @param carParams - параметры новой машины
     * @return экземпляр класса Car - новая машина
     */
    @Override
    public Car createCar(EmptyEngineParams carParams) {
        var engine = new HandEngineI(); // Создаем двигатель без каких-либо параметров

        return new Car(engine); // создаем автомобиль с ручным приводом
    }
}
