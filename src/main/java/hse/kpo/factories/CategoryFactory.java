package hse.kpo.factories;

import org.springframework.stereotype.Component;

import hse.kpo.entities.Category;
import hse.kpo.params.CategoryParams;
import hse.kpo.types.Id;

@Component
public class CategoryFactory {

    public Category createCategory(Id id, CategoryParams params)
    {
        return new Category(id, params.type(), params.name());
    }
}
