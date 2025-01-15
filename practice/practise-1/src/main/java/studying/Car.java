package studying;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Car {

    private Engine engine;

    @Getter
    private int VIN;

    public Car(int VIN, int engineSize) {
        this.VIN = VIN;
        this.engine = new Engine(engineSize);
    }

}
