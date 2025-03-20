package finance.services;

import finance.data.Import.CsvDataImporter;
import finance.data.Import.JsonDataImporter;
import finance.data.Import.YamlDataImporter;

import java.util.List;
import java.util.Map;

public class ImportService {
    private final CsvDataImporter csvDataImporter = new CsvDataImporter();
    private final YamlDataImporter yamlDataImporter = new YamlDataImporter();
    private final JsonDataImporter jsonDataImporter = new JsonDataImporter();

    public List<Map<?, ?>> csvImport(String filePath) {
        return csvDataImporter.importData(filePath);
    }

    public List<Map<?, ?>> yamlImport(String filePath) {
        return yamlDataImporter.importData(filePath);
    }

    public List<Map<?, ?>> jsonImport(String filePath) {
        return jsonDataImporter.importData(filePath);
    }
}
