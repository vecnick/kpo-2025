package HSEBank.Importers.ServiceImport;

import HSEBank.Domains.ExportStructs.ExportServiceStruct;
import HSEBank.Services.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class JsonImporter extends AbstractServiceImporter{
    public ObjectMapper objectMapper;
    public JsonImporter(@Lazy WeakReference<BankAccountService> bankAccountService, String fileName) {
        super(bankAccountService, fileName);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void parseFile() {
        try {
            ExportServiceStruct impService = objectMapper.readValue(reader, ExportServiceStruct.class);
            this.bankAccounts = new ArrayList<>(impService.getAccounts());
            this.categories = new ArrayList<>(impService.getCategories());
            this.operations = new ArrayList<>(impService.getOperations());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
