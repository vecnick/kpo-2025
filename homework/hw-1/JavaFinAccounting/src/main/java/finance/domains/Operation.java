package finance.domains;

import finance.interfaces.IExportVisitor;
import finance.interfaces.IExportable;
import lombok.Getter;

import java.time.LocalDate;

public class Operation implements IExportable {
    @Getter
    private int id;

    @Getter
    private int type;

    @Getter
    private double amount;

    @Getter
    private LocalDate date;

    @Getter
    private int bankAccountId;

    @Getter
    private int categoryId;

    @Getter
    private String description;

    private Operation(OperationBuilder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.amount = builder.amount;
        this.date = builder.date;
        this.bankAccountId = builder.bankAccountId;
        this.categoryId = builder.categoryId;
        this.description = builder.description;
    }

    public static class OperationBuilder {
        private int id;

        private int type;

        private double amount;

        private LocalDate date;

        private int bankAccountId;

        private int categoryId;

        private String description;

        public OperationBuilder(int id, int type, double amount) {
            this.id = id;
            this.type = type;
            this.amount = amount;
        }

        public OperationBuilder date(LocalDate date) {
            this.date = date;

            return this;
        }

        public OperationBuilder bankAccountId(int bankAccountId) {
            this.bankAccountId = bankAccountId;

            return this;
        }

        public OperationBuilder categoryId(int categoryId) {
            this.categoryId = categoryId;

            return this;
        }

        public OperationBuilder description(String description) {
            this.description = description;

            return this;
        }

        public Operation build() {
            return new Operation(this);
        }
    }

    @Override
    public void accept(IExportVisitor visitor, String fileName, boolean append) {
        visitor.visit(this, fileName, append);
    }

    @Override
    public String toString() {
        return "Operation " + id +
                "\ntype: " + (type == 0 ? "Income" : "Expenses") +
                "\namount: " + amount +
                "\ndate: " + date +
                "\naccountId: " + bankAccountId +
                "\ncategoryId: " + categoryId +
                "\ndescription: " + description;
    }
}
