package hse.kpo.types;

import lombok.Getter;
import lombok.ToString;

import hse.kpo.exceptions.ValueException;

@ToString
public class Id {
    @Getter
    int id;

    public Id(int id) throws ValueException
    {
        if (id < 0)
        {
            throw new ValueException("Id must be positive");
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Id)obj).getId();
    }
}

