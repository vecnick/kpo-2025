package studying;

/**
 * A factory that creates cars with pedal engines.
 */
public class PedalCarFactory implements ICarFactory<PedalEngineParams>{

    /**
     * Creates a car with a pedal engine.
     *
     * @param carParams the parameters to configure the pedal engine
     * @param carNumber the identification number of the car
     * @return the created car with the specified pedal engine and number
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(carNumber, engine);
    }
}

