package bank.domains;

import bank.enums.OperationType;
import bank.report.ReportCategory;
import bank.report.ReportOperation;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Операция
 */
@ToString
@Getter
public class Operation {
    private int id;
    private OperationType type;
    private BankAccount bankAccountId;
    private double amount;
    private LocalDateTime date;
    private String description;
    private Category categoryId;

    /**
     * Создание операции
     *
     * @param id - id операции
     * @param type - тип операции (доход или расход)
     * @param bankAccountId - счёт, к которому относится операция
     * @param amount - сумма операции
     * @param date - дата операции
     * @param description - описание операции
     * @param categoryId - категория, к которому относится операция
     */
    public Operation(int id, OperationType type, BankAccount bankAccountId, double amount, LocalDateTime date, String description, Category categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Operation(ReportOperation report) {
        this.id = report.id;
        this.type = report.type;
        this.bankAccountId = report.bankAccountId;
        this.amount = report.amount;
        this.date = report.date;
        this.description = report.description;
        this.categoryId = report.categoryId;
    }
}
