package erp.domains;

import erp.interfaces.IAnimalType;

public class Herbo implements IAnimalType {
    private int kindness;

    @Override
    public boolean isInteractiveAllowed() {
        return kindness > 5;
    }

    public Herbo(int kindness) { this.kindness = kindness; }
}
