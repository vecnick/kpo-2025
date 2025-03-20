package HSEBank.Importers.ServiceImport;

import HSEBank.Domains.ExportStructs.ExportServiceStruct;
import HSEBank.Services.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class YamlImporter extends AbstractServiceImporter{
    public YamlImporter(WeakReference<BankAccountService> bankAccountService, String fileName) {
        super(bankAccountService, fileName);
    }

    @Override
    public void parseFile() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        try {
            objectMapper.registerModule(new JavaTimeModule());

            ExportServiceStruct impService = objectMapper.readValue(reader, ExportServiceStruct.class);
            this.bankAccounts = new ArrayList<>(impService.getAccounts());
            this.categories = new ArrayList<>(impService.getCategories());
            this.operations = new ArrayList<>(impService.getOperations());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}