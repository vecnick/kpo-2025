package hse.kpo.services;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.domains.Ships.Ship;
import hse.kpo.factories.Ships.ShipFactory;

import java.util.ArrayList;
import java.util.List;

import hse.kpo.interfaces.CreationObserver;
import hse.kpo.domains.params.EmptyEngineParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Класс поставщика машин.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ShipService {
    @Getter
    private final List<Ship> ships = new ArrayList<>();

    private int carNumberCounter = 0;

    final List<CreationObserver> observers = new ArrayList<>();

    public void addObserver(CreationObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Types productType, int vin) {
        observers.forEach(obs -> obs.onCreation(productType, vin));
    }

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

        log.debug("found ship {} with customer {}", firstShip, customer);

        return firstShip.orElse(null);
    }

    public Ship addCar(ShipFactory shipFactory, EmptyEngineParams carParams) {
        // создаем автомобиль из переданной фабрики
        var car = shipFactory.createShip(
                carParams // передаем номер - номер будет начинаться с 1
        );
        notifyObserversForSale(Types.SHIP, car.getVin());
        ships.add(car); // добавляем автомобиль
        return car;
    }

    public Ship addExistingShip(Ship car) {
        notifyObserversForSale(Types.CAR, car.getVin());
        ships.add(car);
        return car;
    }
}
