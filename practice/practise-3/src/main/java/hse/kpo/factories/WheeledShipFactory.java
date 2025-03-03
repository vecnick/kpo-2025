package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.HandEngineI;
import hse.kpo.domains.WheeledShip;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WheeledShipFactory implements CarFactoryI<EmptyEngineParams> {
    final ShipFactory shipFactory;
    //TODO: inner vins for ships

    /**
     * Метод, создающий машину.
     *
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса Car - новая машина
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var ship = shipFactory.createShip(carParams, carNumber);
        return new WheeledShip(ship);
    }
}
