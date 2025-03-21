package hse.kpo.interfaces;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Category;
import hse.kpo.entities.Operation;

public interface Visitor<T> {

    // void visit(BankAccount account);
    // void visit(Category category);
    // void visit(Operation operation);
    void visit(T entity);
}
