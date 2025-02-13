package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public class Computer extends Thing {
    public Computer(int inventoryNumber) {
        super(inventoryNumber);
    }
}
