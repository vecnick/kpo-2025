package hse.kpo.storages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import hse.kpo.interfaces.IdEntity;
import hse.kpo.interfaces.Visitor;
import hse.kpo.types.Id;
import lombok.Getter;



// @Component
public class IdEntityStorage <T extends IdEntity> {

    @Getter
    List<T> entities = new ArrayList<>();


    public void add(T account) {
        entities.add(account);
    }

    public Optional<T> get(Id id) {
        return entities.stream().filter(account -> account.getId().equals(id)).findFirst();
    }

    public void remove(Id id) {
        entities.removeIf(account -> account.getId().equals(id));
    }

    public void accept(Visitor<T> visitor) {
        for (T elem : entities) {
            visitor.visit(elem);
        }
    }
}
