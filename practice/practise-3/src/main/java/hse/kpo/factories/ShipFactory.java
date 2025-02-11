package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.HandEngineI;
import hse.kpo.domains.Ship;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс, производящий машины с ручным двигателем.
 */
@Component
public class ShipFactory {
    /**
     * Метод, создающий машину.
     *
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса Car - новая машина
     */
    public Ship createShip(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngineI(); // Создаем двигатель без каких-либо параметров

        return new Ship(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
