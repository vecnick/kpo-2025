package hse.kpo.factories;


import hse.kpo.domains.Car;
import hse.kpo.domains.PedalEngineI;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс фабрики педальных автомобилей.
 */
@Component
public class PedalCarFactoryI implements CarFactoryI<PedalEngineParams> {
    /**
     * Метод, позволяющий создать машину с данными параметрами.
     *
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса car новой машины
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngineI(carParams.pedalSize());

        return new Car(carNumber, engine);
    }
}

