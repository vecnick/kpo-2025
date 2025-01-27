/**
 * A factory that creates cars with manual engines.
 *
 */
package studying;

public class HandCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     * Creates a car with a manual engine.
     *
     * @param carParams the parameters to pass to the engine
     * @param carNumber the number of the car
     * @return the created car
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Creates a manual engine with no parameters

        return new Car(carNumber, engine); // Creates a car with the manual engine
    }
}

