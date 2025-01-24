package studying.domains;

import lombok.Getter;
import lombok.ToString;
import studying.interfaces.IEngine;

@ToString
public class Car {
    private IEngine engine;

    @Getter
    private int VIN;

    public Car(int VIN, IEngine engine) {
        this.VIN = VIN;
        this.engine = engine;
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
