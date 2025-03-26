package studying.factories;

import studying.domains.Car;
import studying.domains.LevitatingEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;

/**
 * Класс фабрики по созданию автомобилей с левитирующим двигателем
 */
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * Создаёт автомобиль с левитирующим двигателем
     *
     * @param carParams - параметры автомобиля (для данного класса они пустые)
     * @param carNumber - порядковый номер автомобиля на производстве
     * @return объект автомобиля с двигателем с левитирующим двигателем
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine);
    }
}
