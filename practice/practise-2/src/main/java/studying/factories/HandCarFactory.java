package studying.factories;

import studying.domains.Car;
import studying.domains.HandEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

/**
 * Класс фабрики по созданию автомобилей с двигателем с ручным приводом
 */
public class HandCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * Создаёт автомобиль с двигателем с ручным приводом
     *
     * @param carParams - параметры автомобиля (для данного класса они пустые)
     * @param carNumber - порядковый номер автомобиля на производстве
     * @return объект автомобиля с двигателем с ручным приводом
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
