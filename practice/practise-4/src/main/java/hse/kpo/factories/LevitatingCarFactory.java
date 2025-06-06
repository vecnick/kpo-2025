package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.LevitatingEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс фабрики по созданию автомобилей с левитирующим двигателем
 */
@Component // Чтобы сделать сервис видимым контексту spring, нужно добавить аннотацию Component
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
