package hse.kpo.factories;


import hse.kpo.domains.Car;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс фабрики по созданию автомобилей с двигателем с педалями
 */
@Component // Чтобы сделать сервис видимым контексту spring, нужно добавить аннотацию Component
public class PedalCarFactory implements ICarFactory<PedalEngineParams> {
    /**
     * Создаёт автомобиль с двигателем с педалями
     *
     * @param carParams - параметры автомобиля (для данного класса параметром является размер двигателя (size))
     * @param carNumber - порядковый номер автомобиля на производстве
     * @return объект автомобиля с двигателем с двигателем с педалями
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Car(carNumber, engine); // возвращаем собранный автомобиль с установленным двигателем и определенным номером
    }
}
