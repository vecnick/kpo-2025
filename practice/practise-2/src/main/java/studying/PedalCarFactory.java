package studying;

/**
 * Класс фабрики педальных автомобилей
 */
public class PedalCarFactory implements ICarFactory<PedalEngineParams>{
    /**
     * Метод, позволяющий создать машину с данными параметрами
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return экземпляр класса car новой машины
     */
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Car(carNumber, engine); // возвращаем собранный автомобиль с установленным двигателем и определенным номером
    }
}
