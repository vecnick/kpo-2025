package bank.domains;

import bank.enums.OperationType;
import bank.report.ReportBankAccount;
import bank.report.ReportCategory;
import lombok.Getter;
import lombok.ToString;

/**
 * Описание видов категорий, к которым относится операция
 */
@ToString
@Getter
public class Category {
    private final int id;
    private final OperationType type;
    private final String name;

    /**
     * Создание категорий
     *
     * @param id - id категории
     * @param type - тип категории (доход или расход)
     * @param name - название категории
     */
    public Category(int id, OperationType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public Category(ReportCategory report) {
        this.id = report.id;
        this.type = report.type;
        this.name = report.name;
    }
}
