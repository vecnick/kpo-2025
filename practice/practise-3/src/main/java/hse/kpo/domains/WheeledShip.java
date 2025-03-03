package hse.kpo.domains;

import hse.kpo.interfaces.EngineI;
import lombok.ToString;

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
