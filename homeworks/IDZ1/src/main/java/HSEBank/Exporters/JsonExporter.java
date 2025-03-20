package HSEBank.Exporters;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Categories.Category;
import HSEBank.Domains.ExportStructs.ExportServiceStruct;
import HSEBank.Domains.Operations.Operation;
import HSEBank.Interfaces.ExporterI;
import HSEBank.Services.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonExporter implements ExporterI {
    String fileName;
    private final ObjectMapper objectMapper;
    ExportServiceStruct exportServiceStruct;


    public JsonExporter(String fileName) {
        this.fileName = fileName;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.exportServiceStruct = new ExportServiceStruct();
    }

    @Override
    public void exportOperations(List<Operation> operations) {
        exportServiceStruct.setOperations(operations);
    }

    @Override
    public void exportAccounts(List<BankAccount> accounts) {
        exportServiceStruct.setAccounts(accounts);
    }

    @Override
    public void exportCategories(List<Category> categories) {
        exportServiceStruct.setCategories(categories);
    }

    @Override
    public void export() {
        try {
            objectMapper.writeValue(new File(fileName), exportServiceStruct);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
