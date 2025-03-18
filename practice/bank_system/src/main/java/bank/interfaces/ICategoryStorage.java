package bank.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.enums.OperationType;

import java.util.List;

public interface ICategoryStorage {
    void addCategory(ICategoryFactory categoryFactory, OperationType type, String name);

    List<Category> getCategories();
}
