package hse.kpo.domains.WheeledShips;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.domains.Ships.Ship;

public class WheeledShip  extends Car {
    private final Ship ship;

    public WheeledShip(Ship ship) {
        super(ship.getVin(), ship.getEngine());
        this.ship = ship;
    }

    @Override
    public boolean isCompatible(Customer customer) {
        return super.isCompatible(customer) && ship.isCompatible(customer);
    }

    @Override
    public String toString() {
        return "WheeledShip{" +
                "ship=" + ship +
                "car=" + super.toString() +
                '}';
    }
}
