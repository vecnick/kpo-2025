package finance.data.Import;

import finance.domains.Category;
import finance.domains.Operation;
import finance.factories.BankAccountFactory;
import finance.factories.CategoryFactory;
import finance.factories.OperationFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DataImporter {
    public final List<Map<?, ?>> importData(String filePath) {
        try {
            String data = readFile(filePath);

            return (List<Map<?, ?>>) parseData(data);
        } catch (IOException e) {
            throw new RuntimeException("Import error " + filePath, e);
        }
    }

    private String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    protected abstract List<?> parseData(String data);
}