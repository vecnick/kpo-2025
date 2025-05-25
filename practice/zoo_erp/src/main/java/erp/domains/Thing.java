package erp.domains;

import erp.interfaces.IInventory;
import lombok.ToString;

@ToString
public class Thing implements IInventory {
    private final int number;

    @Override
    public int getNumber() {
        return number;
    }

    public Thing(int number) {
        this.number = number;
    }
}
