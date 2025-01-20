package studying;

import lombok.ToString;

@ToString
public class HandEngine implements IEngine{
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.handPower > 5;
    }
}
