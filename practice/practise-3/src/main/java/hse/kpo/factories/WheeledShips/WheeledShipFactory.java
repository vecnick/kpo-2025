package hse.kpo.factories.WheeledShips;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.WheeledShips.WheeledShip;
import hse.kpo.factories.Ships.ShipFactory;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.domains.params.EmptyEngineParams;
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
