package hse.kpo.entities;

import hse.kpo.enums.OperationType;
import hse.kpo.interfaces.IdEntity;
import hse.kpo.types.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Category implements IdEntity {
    Id id;
    OperationType type;
    String name;

    public Category(Id id, OperationType type, String name)
    {
        this.id = id;
        this.type = type;
        this.name = name;
    }

}

