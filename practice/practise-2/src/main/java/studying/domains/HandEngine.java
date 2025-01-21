package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

@ToString
public class HandEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
