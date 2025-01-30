package studying.factories;

import studying.domains.Car;
import studying.domains.HandEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

/**
 * Фарбрика машин с ручным двигателем
 */
public class HandCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * Создаёт объект Car с ручным двигателем
     *
     * @param carParams параметры машины
     * @param carNumber номер машины
     * @return объект класса Car
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
