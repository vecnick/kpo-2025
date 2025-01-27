package studying;

/**
 * A class that implements the ICarFactory interface and produces levitation cars
 */
public class LevitationCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * A method that creates a car
     * @param carNumber the number of the car
     * @return the created car
     */
    @Override
    public Car createCar(int carNumber) {
        var engine = new LevitationEngine(); // Create a levitation engine with no parameters
        return new Car(carNumber, engine); // Create a car with the levitation engine
    }
}

