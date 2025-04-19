package hse.kpo.factories.Ships;

import hse.kpo.domains.Engines.HandEngineI;
import hse.kpo.domains.Ships.Ship;
import hse.kpo.domains.params.EmptyEngineParams;
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
     * @return экземпляр класса Car - новая машина
     */
    public Ship createShip(EmptyEngineParams carParams) {
        var engine = new HandEngineI(); // Создаем двигатель без каких-либо параметров

        return new Ship(engine); // создаем автомобиль с ручным приводом
    }
}
