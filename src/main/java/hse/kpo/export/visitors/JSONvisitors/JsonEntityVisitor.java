package hse.kpo.export.visitors.JSONvisitors;

import java.util.ArrayList;
import java.util.List;

import hse.kpo.interfaces.Visitor;
import lombok.Getter;

public class JsonEntityVisitor<T> implements Visitor<T> {
    @Getter
    List<Object> objects;

    @Override
    public void visit(T entity) {
        objects.add(entity);
    }

    public void clear() {
        objects = new ArrayList<Object>();
    }    
}
