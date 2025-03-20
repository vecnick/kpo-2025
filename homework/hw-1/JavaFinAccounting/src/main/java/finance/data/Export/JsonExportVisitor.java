package finance.data.Export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import finance.domains.BankAccount;
import finance.domains.Category;
import finance.domains.Operation;
import finance.interfaces.IExportVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonExportVisitor implements IExportVisitor {
    private final String basePath;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonExportVisitor(String basePath) {
        this.basePath = basePath;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        createExportDirectory();
    }

    private void createExportDirectory() {
        try {
            Files.createDirectories(Paths.get(basePath));
        } catch (IOException e) {
            throw new RuntimeException("Did not manage to create directory " + basePath, e);
        }
    }

    private void exportBankAccountToJson(BankAccount account, String filePath, boolean append) {
        Map<String, Object> data = new HashMap<>();
        data.put("ID", account.getId());
        data.put("Name", account.getName());
        data.put("Balance", account.getBalance());

        writeJson(data, filePath, append);
    }

    private void exportCategoryToJson(Category category, String filePath, boolean append) {
        Map<String, Object> data = new HashMap<>();
        data.put("ID", category.getId());
        data.put("Type", category.getType() == 0 ? "Income" : "Expenses");
        data.put("Name", category.getName());

        writeJson(data, filePath, append);
    }

    private void exportOperationToJson(Operation operation, String filePath, boolean append) {
        Map<String, Object> data = new HashMap<>();
        data.put("ID", operation.getId());
        data.put("Type", operation.getType() == 0 ? "Income" : "Expenses");
        data.put("Amount", operation.getAmount());
        data.put("Date", operation.getDate().toString());
        data.put("BankAccountID", operation.getBankAccountId());
        data.put("CategoryID", operation.getCategoryId());
        data.put("Description", operation.getDescription());

        writeJson(data, filePath, append);
    }

    private void writeJson(Map<String, Object> data, String filePath, boolean append) {
        try {
            File file = new File(filePath);

            List<Map<String, Object>> dataList = new ArrayList<>();

            if (append && file.exists()) {
                dataList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
            }

            dataList.add(data);

            objectMapper.writeValue(file, dataList);
        } catch (IOException e) {
            throw new RuntimeException("JSON Export Error " + filePath, e);
        }
    }

    @Override
    public void visit(BankAccount bankAccount, String fileName, boolean append) {
        exportBankAccountToJson(bankAccount, basePath + fileName, append);
    }

    @Override
    public void visit(Category category, String fileName, boolean append) {
        exportCategoryToJson(category, basePath + fileName, append);
    }

    @Override
    public void visit(Operation operation, String fileName, boolean append) {
        exportOperationToJson(operation, basePath + fileName, append);
    }
}