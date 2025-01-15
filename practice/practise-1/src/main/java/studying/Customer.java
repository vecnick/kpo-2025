package studying;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Customer {
    @Getter
    private String name;

    @Getter
    @Setter
    private Car car;

    public Customer(String name) {
        this.name = name;
    }

}
