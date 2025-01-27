package studying;

import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 *     This class represents a car.
 * </p>
 */
@ToString
public class Car {

    private IEngine engine;

    /**
     * Get the VIN (Vehicle Identification Number) of the car.
     * @return the VIN of the car
     */
    @Getter
    private int VIN;

    /**
     * Creates a new car with specified VIN and engine.
     * @param VIN the VIN of the car
     * @param engine the engine of the car
     */
    public Car(int VIN, IEngine engine) {
        this.VIN = VIN;
        this.engine = engine;
    }

    /**
     * Checks if the car is compatible with the customer.
     * @param customer the customer to check
     * @return true if the car is compatible with the customer, false otherwise
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // inside the method just call the corresponding method of the engine
    }
}

