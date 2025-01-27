package studying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a customer with attributes such as name, leg power, hand power, IQ, and an associated car.
 */
@Getter
@ToString
public class Customer {
    private final String name;
    private final int legPower;
    private final int handPower;
    private final int iq;
    
    @Setter
    private Car car;

    /**
     * Constructs a new Customer with the specified attributes.
     *
     * @param name the name of the customer
     * @param legPower the leg power of the customer
     * @param handPower the hand power of the customer
     * @param iq the IQ of the customer
     */
    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}

