package studying.factories;

import studying.domains.Car;
import studying.domains.HandEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

public class HandCarFactory implements ICarFactory<EmptyEngineParams> {

    /**
     * Метод автомобильной фабрики по сборке ручных автомобилей для создания автомобиля.
     * 
     * @param carParams - параметры создаваемого автомобиля EmptyEngineParams
     * @param carNumber - номер автомобиля
     * @return - созданный автомобиль
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
