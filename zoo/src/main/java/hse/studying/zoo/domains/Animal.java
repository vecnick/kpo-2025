package hse.studying.zoo.domains;

import hse.studying.zoo.interfaces.IAlive;
import hse.studying.zoo.interfaces.IInventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


/**
 * Abstract class representing an animal.
 * All animals have an inventory number, a food consumption and a weight.
 */
@ToString
@RequiredArgsConstructor
public abstract class Animal implements IAlive, IInventory {
    private final int foodConsumption;

    @Getter
    private final int weight;

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
