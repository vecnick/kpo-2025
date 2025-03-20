package HSEBank.Factories;

import HSEBank.Importers.ServiceImport.AbstractServiceImporter;
import HSEBank.Importers.ServiceImport.CsvImporter;
import HSEBank.Importers.ServiceImport.JsonImporter;
import HSEBank.Importers.ServiceImport.YamlImporter;
import HSEBank.Interfaces.ImporterFactoryI;
import HSEBank.Services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.ref.WeakReference;

@Component
public class ImporterFactory implements ImporterFactoryI {
    private final WeakReference<BankAccountService> bankAccountService;

    ImporterFactory(@Lazy BankAccountService bankAccountService) {
        this.bankAccountService = new WeakReference<>(bankAccountService);
    }

    @Override
    public AbstractServiceImporter createImporter(String fileName) {
        var parts = fileName.split("\\.");

        if (parts.length == 2) {
            switch (parts[1]) {
                case "csv":
                    return new CsvImporter(bankAccountService, fileName);
                case "json":
                    return new JsonImporter(bankAccountService, fileName);
                case "yaml":
                    return new YamlImporter(bankAccountService, fileName);
                default:
                    return null;
            }
        } else {
           return null;
        }
    }
}
