package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

@ToString
public class LevitatingEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIQ() > 300;
    }
}
