package bank.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

public interface ICategoryFactory {
    Category create(int id, OperationType type, String name);
}
