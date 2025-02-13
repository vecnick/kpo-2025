package hse.studying.zoo.params;

/**
 * Represents the parameters for a {@link hse.studying.zoo.domains.Predator}.
 *
 * @param foodConsumption the amount of food consumed per day (in kilograms)
 * @param weight          the weight of the predator (in kilograms)
 * @param inventoryNumber the inventory number assigned to the predator
 */
public record PredatorParams(int foodConsumption, int weight, int inventoryNumber) {
}
