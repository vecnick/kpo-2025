package hse.kpo.facades;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.entities.Operation;
import hse.kpo.exceptions.ValueException;
import hse.kpo.factories.OperationFactory;
import hse.kpo.params.OperationParams;
import hse.kpo.storages.IdEntityStorage;
import hse.kpo.types.Id;


@Component
public class OperationFacade {
    OperationFactory factory;
    IdEntityStorage<Operation> storage;
    int max_id = 0;

    @Autowired
    public OperationFacade(OperationFactory factory, IdEntityStorage<Operation> storage)
    {
        this.factory = factory;
        this.storage = storage;
    }

    public List<Operation> getOperations()
    {
        return storage.getEntities();
    }

    public void createOperation(OperationParams params) throws ValueException
    {
        storage.add(factory.createOperation(new Id(max_id), params));
        max_id++;
    }

    public Optional<Operation> getOperationbyId(Id id)
    {
        return storage.get(id);
    }

    public void removeOperation(Id id)
    {
        storage.remove(id);
    }

    public List<Operation> getOperationsByBankAccountId(Id bankAccountId) {
        return storage.getEntities().stream()
                      .filter(operation -> operation.getBank_account_id().equals(bankAccountId))
                      .collect(Collectors.toList());
    }

    public List<Operation> getOperationsByCategoryId(Id categoryId) {
        return storage.getEntities().stream()
                      .filter(operation -> operation.getCategory_id().equals(categoryId))
                      .collect(Collectors.toList());
    }
}
