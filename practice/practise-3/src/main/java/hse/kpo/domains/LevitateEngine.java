package hse.kpo.domains;

import lombok.ToString;
import hse.kpo.interfaces.IEngine;


@ToString
public class LevitateEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIQ() > 300;
    }
}
