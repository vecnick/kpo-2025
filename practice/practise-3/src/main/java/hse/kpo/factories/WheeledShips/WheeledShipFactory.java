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

    /**
     * Метод, создающий машину.
     *
     * @param carParams - параметры новой машины
     * @return экземпляр класса Car - новая машина
     */
    @Override
    public Car createCar(EmptyEngineParams carParams) {
        var ship = shipFactory.createShip(carParams);
        return new WheeledShip(ship);
    }

}
