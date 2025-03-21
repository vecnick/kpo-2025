package bank.Factories;

import bank.domains.Category;
import bank.enums.OperationType;
import bank.interfaces.ICategoryFactory;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory implements ICategoryFactory {

    @Override
    public Category create(int id, OperationType type, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным");
        }
        if (name == "") {
            name = "Other";
        }
        return new Category(id, type, name);
    }
}
