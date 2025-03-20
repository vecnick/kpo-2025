package finance.interfaces;

import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;

public interface IExportVisitor {
    void visit(BankAccount bankAccount, String fileName, boolean append);
    void visit(Category category, String fileName, boolean append);
    void visit(Operation operation, String fileName, boolean append);
}
