package hse.studying.zoo.domains;

import hse.studying.zoo.interfaces.IInventory;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public abstract class Thing implements IInventory {
    private final int inventoryNumber;

    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }
}
