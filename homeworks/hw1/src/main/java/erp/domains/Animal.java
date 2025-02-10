package erp.domains;

import erp.interfaces.IAlive;

public abstract class Animal implements IAlive {
    protected String name;

    @Override
    public int getFood() {
        return food;
    }
}
