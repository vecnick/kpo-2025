package erp.services;

import erp.interfaces.IApprover;
import org.springframework.stereotype.Component;

@Component
public class VetClinic implements IApprover {
    @Override
    public boolean isApproved(int health) {
        return health > 5;
    }
}
