package hse.kpo.facades;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Category;
import hse.kpo.exceptions.ValueException;
import hse.kpo.factories.CategoryFactory;
import hse.kpo.params.CategoryParams;
import hse.kpo.storages.IdEntityStorage;
import hse.kpo.types.Id;



@Component
public class CategoryFacade {
    CategoryFactory factory;
    IdEntityStorage<Category> storage;
    int max_id = 0;

    @Autowired
    public CategoryFacade(CategoryFactory factory, IdEntityStorage<Category> storage)
    {
        this.factory = factory;
        this.storage = storage;
    }

    public List<Category> getCategories()
    {
        return storage.getEntities();
    }

    public void createCategory(CategoryParams params) throws ValueException
    {
        storage.add(factory.createCategory(new Id(max_id), params));
        max_id++;
    }

    public Optional<Category> getCategorybyId(Id id)
    {
        return storage.get(id);
    }

    public void removeCategory(Id id)
    {
        storage.remove(id);
    }

}
