package erp.interfaces;

import erp.domains.Enclosure;
import erp.enums.Health;

import java.util.Optional;

public interface IAlive {

    IAnimalType getAnimalType();
    String getName();
    Optional<IEnclosure> getCurrentEnclosure();


    void feed();
    void heal();
    void moveToEnclosure(IEnclosure enclosure);
}

