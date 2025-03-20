package bank.report;

import bank.domains.Category;
import bank.enums.OperationType;

public class ReportCategory {
    public int id;
    public OperationType type;
    public String name;

    public ReportCategory(Category category) {
        this.id = category.getId();
        this.type = category.getType();
        this.name = category.getName();
    }
}
