package hse.kpo.services;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.domains.Ship;
import hse.kpo.factories.ShipFactory;
import hse.kpo.interfaces.CarFactoryI;
import hse.kpo.interfaces.CarProviderI;
import java.util.ArrayList;
import java.util.List;

import hse.kpo.params.EmptyEngineParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс поставщика машин.
 */
@Component
@RequiredArgsConstructor
public class ShipService {

    private final List<Ship> ships = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     * Функция дающая машину покупателю.
     *
     * @param customer - покупатель, для которого ищем машину
     * @return машину или null.
     */
    public Ship takeShip(Customer customer) {

        var filteredShips = ships.stream().filter(ship -> ship.isCompatible(customer)).toList();

        var firstShip = filteredShips.stream().findFirst();

        firstShip.ifPresent(ships::remove);

        return firstShip.orElse(null);
    }

    public void addCar(ShipFactory shipFactory, EmptyEngineParams carParams) {
        // создаем автомобиль из переданной фабрики
        var car = shipFactory.createShip(
                carParams, // передаем параметры
                ++carNumberCounter // передаем номер - номер будет начинаться с 1
        );

        ships.add(car); // добавляем автомобиль
    }
}
