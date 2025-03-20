package finance.data.Import;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class CsvDataImporter extends DataImporter {
    @Override
    protected List<?> parseData(String data) {
        List<Map<String, String>> result = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new StringReader(data))) {
            String[] header = reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < header.length; i++) {
                    row.put(header[i], line[i]);
                }
                result.add(row);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("CSV parsing error ", e);
        }

        return result;
    }
}