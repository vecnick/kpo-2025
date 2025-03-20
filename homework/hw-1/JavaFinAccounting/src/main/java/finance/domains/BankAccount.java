package finance.domains;

import finance.interfaces.IExportVisitor;
import finance.interfaces.IExportable;
import lombok.Getter;

public class BankAccount implements IExportable {
    @Getter
    private int id;

    @Getter
    private String name;

    @Getter
    private double balance;

    public BankAccount(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void accept(IExportVisitor visitor, String fileName, boolean append) {
        visitor.visit(this, fileName, append);
    }

    @Override
    public String toString() {
        return "Bank account " + name +
                "\nid: " + id +
                "\nbalance: " + balance;
    }
}
