/**
 * A hand-powered engine.
 */
@ToString
public class HandEngine implements IEngine {
    /**
     * Checks if the engine is compatible with the customer.
     * @param customer the customer to check
     * @return true if the engine is compatible with the customer, false otherwise
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}

