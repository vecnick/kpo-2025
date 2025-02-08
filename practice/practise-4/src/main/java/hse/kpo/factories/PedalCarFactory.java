package hse.kpo.factories;


import hse.kpo.domains.Car;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

@Component
public class PedalCarFactory implements ICarFactory<PedalEngineParams> {
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Car(carNumber, engine); // возвращаем собранный автомобиль с установленным двигателем и определенным номером
    }
}
