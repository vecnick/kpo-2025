package hse.studying.zoo.interfaces;

/**
 * Interface for any living object in the zoo.
 * Any living object is characterized by food consumption per day.
 */
public interface AliveInterface {
    /**
     * Get food consumption per day.
     *
     * @return food consumption for this alive object
     */
    int getFoodConsumption();
}
