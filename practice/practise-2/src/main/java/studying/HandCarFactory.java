package studying;

/**
 * Класс, производящий машины с ручным двигателем
 */
public class HandCarFactory implements ICarFactory<EmptyEngineParams>{
    /**
     * Метод, создающий машину
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса Car - новая машина
     */
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
