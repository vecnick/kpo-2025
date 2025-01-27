package studying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a levitation engine.
 */
@ToString
@Getter
public class LevitationEngine implements IEngine {

    /**
     * Checks if the engine is compatible with the customer.
     * 
     * @param customer the customer to check
     * @return true if the customer's IQ is 300 or higher, false otherwise
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() >= 300;
    }
}

