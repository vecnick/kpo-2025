package hse.studying.zoo.params;

/**
 * Represents the parameters for a {@link hse.studying.zoo.domains.Herbo}.
 *
 * @param foodConsumption the amount of food consumed per day (in kilograms)
 * @param weight          the weight of the herbivore (in kilograms)
 * @param kindness        the kindness level of the herbivore (scale from 1 to 10)
 * @param inventoryNumber the inventory number assigned to the herbivore
 */
public record HerbivoreParams(int foodConsumption, int weight, int kindness, int inventoryNumber) {
}
