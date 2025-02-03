package studying;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс машины
 */
@ToString
public class Car {

    private IEngine engine;

    @Getter
    private int VIN;

    /**
     *
     * @param VIN - номер новой машины
     * @param engine - двигатель новой машины
     */
    public Car(int VIN, IEngine engine) {
        this.VIN = VIN;
        this.engine = engine;
    }

    /**
     * Метод, проверяющий, подходит ли машина покупателю
     * @param customer - пользователь, для которого проверяем машину
     * @return true, если машина совместима с пользователем
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
