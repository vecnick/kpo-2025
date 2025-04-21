package erp.interfaces;

import erp.domains.Animal;

public interface IEnclosure {
    void add(IAlive alive);
    void remove(String name);
    void clean();
}
