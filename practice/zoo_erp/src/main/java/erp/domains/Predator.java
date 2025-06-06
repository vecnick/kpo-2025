package erp.domains;

import erp.interfaces.IAnimalType;

public class Predator implements IAnimalType {
    public boolean isInteractiveAllowed() {
        return false;
    }
}
