package hse.studying.zoo.domains;

import hse.studying.zoo.interfaces.InventoryInterface;
import hse.studying.zoo.params.ThingParams;
import lombok.ToString;

/**
 * Abstract class for things in zoo.
 * Things have inventory number.
 */
@ToString
public abstract class Thing implements InventoryInterface {
    private final int inventoryNumber;

    public Thing(ThingParams params) {
        inventoryNumber = params.inventoryNumber();
    }

    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }
}
