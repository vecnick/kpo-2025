package bank.report;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

import java.time.LocalDateTime;

public class ReportOperation {
    public int id;
    public OperationType type;
    public int bankAccountId;
    public double amount;
    public LocalDateTime date;
    public String description;
    public int categoryId;

    public ReportOperation(Operation operation) {
        this.id = operation.getId();
        this.type = operation.getType();
        this.bankAccountId = operation.getBankAccountId().getId();
        this.amount = operation.getAmount();
        this.date = operation.getDate();
        this.description = operation.getDescription();
        this.categoryId = operation.getCategoryId().getId();
    }
}
