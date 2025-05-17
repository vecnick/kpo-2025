package bank.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

import java.time.LocalDateTime;

public interface IOperationFactory {
    Operation create(int id, OperationType type, BankAccount bankAccountId, double amount, LocalDateTime date, String description, Category categoryId);
}
