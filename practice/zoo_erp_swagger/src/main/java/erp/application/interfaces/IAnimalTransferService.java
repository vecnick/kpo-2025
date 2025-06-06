package erp.application.interfaces;

import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IEnclosure;
import erp.domain.models.Animal;
import erp.domain.models.Enclosure;

public interface IAnimalTransferService {
    public boolean transfer(IEnclosure enclosure, IAnimal animal);
}
