package finance.domains;

import finance.interfaces.IExportVisitor;
import finance.interfaces.IExportable;
import lombok.Getter;

public class Category implements IExportable {
    @Getter
    private int id;

    @Getter
    private int type;

    @Getter
    private String name;

    public Category(int id, int type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    @Override
    public void accept(IExportVisitor visitor, String fileName, boolean append) {
        visitor.visit(this, fileName, append);
    }

    @Override
    public String toString() {
        return "Category " + name +
                "\nid: " + id +
                "\ntype: " + (type == 0 ? "Income" : "Expenses");
    }
}
