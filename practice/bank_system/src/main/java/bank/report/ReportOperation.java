package bank.report;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.utils.Date;

import java.time.LocalDateTime;

public class ReportOperation {
    public int id;
    public OperationType type;
    public int bankAccountId;
    public double amount;
    public String date;
    public String description;
    public int categoryId;

    // Пустой конструктор необходим для импорта
    public ReportOperation() {
        this(0, null, 0, 0, null, null, 0);
    }

    public ReportOperation(int id, OperationType type, int bankAccountId, double amount, String date, String description, int categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    public ReportOperation(Operation operation) {
        this.id = operation.getId();
        this.type = operation.getType();
        this.bankAccountId = operation.getBankAccountId().getId();
        this.amount = operation.getAmount();
        this.date = Date.dateTimeToString(operation.getDate());
        this.description = operation.getDescription();
        this.categoryId = operation.getCategoryId().getId();
    }
}
