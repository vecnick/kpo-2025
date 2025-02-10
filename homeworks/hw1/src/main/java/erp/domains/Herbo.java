package erp.domains;

import java.io.Serializable;

public abstract class Herbo extends Animal {
    private int kindness;

    public int getKindness() {
        return kindness;
    }

    public void setKindness(int kindness) {
        this.kindness = kindness;
    }
}
