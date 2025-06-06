package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс фабрики по созданию автомобилей с двигателем с ручным приводом
 */
@Component // Чтобы сделать сервис видимым контексту spring, нужно добавить аннотацию Component
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
