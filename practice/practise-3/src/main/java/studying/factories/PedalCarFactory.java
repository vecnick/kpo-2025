package studying.factories;

import studying.domains.Car;
import studying.domains.PedalEngine;
import studying.interfaces.ICarFactory;
import studying.params.PedalEngineParams;

public class PedalCarFactory implements ICarFactory<PedalEngineParams> {

    /**
     * Метод автомобильной фабрики по сборке педальных автомобилей для создания автомобиля.
     * 
     * @param carParams - параметры создаваемого автомобиля PedalEngineParams
     * @param carNumber - номер автомобиля
     * @return - созданный автомобиль
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Car(carNumber, engine); // возвращаем собранный автомобиль с установленным двигателем и определенным номером
    }
}
