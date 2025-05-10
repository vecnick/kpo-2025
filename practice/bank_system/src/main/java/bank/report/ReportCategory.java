package bank.report;

import bank.domains.Category;
import bank.enums.OperationType;

public class ReportCategory {
    public int id;
    public OperationType type;
    public String name;

    // Пустой конструктор необходим для импорта
    public ReportCategory() {
        this(0, null, null);
    }

    public ReportCategory(int id, OperationType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public ReportCategory(Category category) {
        this.id = category.getId();
        this.type = category.getType();
        this.name = category.getName();
    }
}
