package studying;

/**
 * Класс-фабрика летающих машин
 */
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     * Метод, создающий новую летающую машину
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса Car - новую машину
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngine();

        return new Car(carNumber, engine);
    }
}
