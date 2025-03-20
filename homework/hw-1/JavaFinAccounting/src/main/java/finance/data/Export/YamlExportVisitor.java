package finance.data.Export;

import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;
import finance.interfaces.IExportVisitor;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class YamlExportVisitor implements IExportVisitor {
    private final String basePath;

    public YamlExportVisitor(String basePath) {
        this.basePath = basePath;
        createExportDirectory();
    }

    private void createExportDirectory() {
        try {
            Files.createDirectories(Paths.get(basePath));
        } catch (IOException e) {
            throw new RuntimeException("Did not manage to create directory " + basePath, e);
        }
    }

    private void exportBankAccountToYaml(BankAccount account, String filePath, boolean append) {
        Map<String, Object> data = new HashMap<>();
        data.put("ID", account.getId());
        data.put("Name", account.getName());
        data.put("Balance", account.getBalance());

        writeYaml(data, filePath, append);
    }

    private void exportCategoryToYaml(Category category, String filePath, boolean append) {
        Map<String, Object> data = new HashMap<>();
        data.put("ID", category.getId());
        data.put("Type", category.getType() == 0 ? "Income" : "Expenses");
        data.put("Name", category.getName());

        writeYaml(data, filePath, append);
    }

    private void exportOperationToYaml(Operation operation, String filePath, boolean append) {
        Map<String, Object> data = new HashMap<>();
        data.put("ID", operation.getId());
        data.put("Type", operation.getType() == 0 ? "Income" : "Expenses");
        data.put("Amount", operation.getAmount());
        data.put("Date", operation.getDate().toString());
        data.put("BankAccountID", operation.getBankAccountId());
        data.put("CategoryID", operation.getCategoryId());
        data.put("Description", operation.getDescription());

        writeYaml(data, filePath, append);
    }

    private void writeYaml(Map<String, Object> data, String filePath, boolean append) {
        Yaml yaml = new Yaml();
        try (FileWriter writer = new FileWriter(filePath, append)) {
            yaml.dump(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("YAML Export Error " + filePath, e);
        }
    }

    @Override
    public void visit(BankAccount bankAccount, String fileName, boolean append) {
        exportBankAccountToYaml(bankAccount, basePath + fileName, append);
    }

    @Override
    public void visit(Category category, String fileName, boolean append) {
        exportCategoryToYaml(category, basePath + fileName, append);
    }

    @Override
    public void visit(Operation operation, String fileName, boolean append) {
        exportOperationToYaml(operation, basePath + fileName, append);
    }
}
