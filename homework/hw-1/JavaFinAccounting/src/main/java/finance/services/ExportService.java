package finance.services;

import finance.data.Export.CsvExportVisitor;
import finance.data.Export.JsonExportVisitor;
import finance.data.Export.YamlExportVisitor;
import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;
import java.util.List;

public class ExportService {
    private final CsvExportVisitor csvExportVisitor = new CsvExportVisitor("./export/csv/");
    private final YamlExportVisitor yamlExportVisitor = new YamlExportVisitor("./export/yaml/");
    private final JsonExportVisitor jsonExportVisitor = new JsonExportVisitor("./export/json/");

    public void csvExportAccounts(List<BankAccount> accounts, String fileName) {
        accounts.getFirst().accept(csvExportVisitor, fileName, false);
        for (int i = 1; i < accounts.size(); i++) {
            accounts.get(i).accept(csvExportVisitor, fileName, true);
        }
    }

    public void csvExportCategories(List<Category> categories, String fileName) {
        categories.getFirst().accept(csvExportVisitor, fileName, false);
        for (int i = 1; i < categories.size(); i++) {
            categories.get(i).accept(csvExportVisitor, fileName, true);
        }
    }

    public void csvExportOperations(List<Operation> operations, String fileName) {
        operations.getFirst().accept(csvExportVisitor, fileName, false);
        for (int i = 1; i < operations.size(); i++) {
            operations.get(i).accept(csvExportVisitor, fileName, true);
        }
    }

    public void yamlExportAccounts(List<BankAccount> accounts, String fileName) {
        accounts.getFirst().accept(yamlExportVisitor, fileName, false);
        for (int i = 1; i < accounts.size(); i++) {
            accounts.get(i).accept(yamlExportVisitor, fileName, true);
        }
    }

    public void yamlExportCategories(List<Category> categories, String fileName) {
        categories.getFirst().accept(yamlExportVisitor, fileName, false);
        for (int i = 1; i < categories.size(); i++) {
            categories.get(i).accept(yamlExportVisitor, fileName, true);
        }
    }

    public void yamlExportOperations(List<Operation> operations, String fileName) {
        operations.getFirst().accept(yamlExportVisitor, fileName, false);
        for (int i = 1; i < operations.size(); i++) {
            operations.get(i).accept(yamlExportVisitor, fileName, true);
        }
    }

    public void jsonExportAccounts(List<BankAccount> accounts, String fileName) {
        accounts.getFirst().accept(jsonExportVisitor, fileName, false);
        for (int i = 1; i < accounts.size(); i++) {
            accounts.get(i).accept(jsonExportVisitor, fileName, true);
        }
    }

    public void jsonExportCategories(List<Category> categories, String fileName) {
        categories.getFirst().accept(jsonExportVisitor, fileName, false);
        for (int i = 1; i < categories.size(); i++) {
            categories.get(i).accept(jsonExportVisitor, fileName, true);
        }
    }

    public void jsonExportOperations(List<Operation> operations, String fileName) {
        operations.getFirst().accept(jsonExportVisitor, fileName, false);
        for (int i = 1; i < operations.size(); i++) {
            operations.get(i).accept(jsonExportVisitor, fileName, true);
        }
    }
}
