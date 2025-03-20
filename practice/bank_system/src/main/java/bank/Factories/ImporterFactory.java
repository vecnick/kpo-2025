package bank.Factories;

import bank.importer.ImporterCSV;
import bank.importer.ImporterJSON;
import bank.importer.ImporterYAML;
import bank.interfaces.ImporterStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImporterFactory<T> {
    public ImporterStrategy<T> create(String filename) {
        Pattern pattern = Pattern.compile("\\.([^.]+)$"); // Регулярка для формата файла
        Matcher matcher = pattern.matcher(filename);

        String format;
        if (matcher.find()) {
            format = matcher.group(1);
        } else {
            throw new IllegalArgumentException("Формат файла не найден: " + filename);
        }

        return switch (format) {
            case "csv" -> new ImporterCSV<T>();
            case "json" -> new ImporterJSON<T>();
            case "yaml" -> new ImporterYAML<T>();
            default -> throw new IllegalArgumentException("Не поддерживаемый формат файла: " + filename);
        };
    }
}
