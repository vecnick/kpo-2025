package studying;

/**
 * Интерфейс фабрики машин с определенными параметрами
 * @param <TParams> - параметры машины
 */
public interface ICarFactory<TParams> {
    /**
     * Метод, позволяющий создать машину с определенными параметрами
     * @param carParams - параметры новой машины
     * @param carNumber - номер новой машины
     * @return - экземпляр класса Car с заданными параметрами
     */
    Car createCar(TParams carParams, int carNumber);
}
