package studying;

public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}
