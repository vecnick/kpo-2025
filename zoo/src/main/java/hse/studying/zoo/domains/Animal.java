package hse.studying.zoo.domains;

import hse.studying.zoo.interfaces.IAlive;
import hse.studying.zoo.interfaces.IInventory;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public abstract class Animal implements IAlive, IInventory {
    private final int foodConsumption;
    private final int inventoryNumber;

    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    @Override
    public int getFoodConsumption() {
        return foodConsumption;
    }
}
